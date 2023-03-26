package martinrahe.fujitsu_internship_test_deliveryprice;

import martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects.*;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.ForbiddenVehicleTypeException;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.VehicleTypeNotFoundException;
import martinrahe.fujitsu_internship_test_deliveryprice.errors.WeatherDataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@RestController
public class ResponseHandler {
    private static final Set<String> allowedVehicles =
            new HashSet<>(Arrays.asList("Car", "Scooter", "Bike"));
    private static final Map<String, String> bestStationForLocation = Map.of("Tallinn", "Tallinn-Harku",
            "Tartu", "Tartu-Tõravere",
            "Pärnu", "Pärnu");

    @Autowired
    private WeatherDataController weatherDataController;

    @Autowired
    private RBFController rbfController;

    @Autowired
    private ATEFController atefController;

    @Autowired
    private WSEFController wsefController;

    @Autowired
    private WPEFController wpefController;

    @GetMapping("/deliveryfee/{city}/{vehicle}/{datetime}")
    private String totalDeliveryFee(@PathVariable String city, @PathVariable String vehicle, @PathVariable LocalDateTime datetime) {
        ZoneId zoneId = ZoneId.systemDefault(); //uses the system timezone
        long timestamp = datetime.atZone(zoneId).toEpochSecond(); //extracts the unix timestamp
        if (!bestStationForLocation.containsKey(city)) {
            throw new WeatherDataNotFoundException();
        }
        String station = bestStationForLocation.get(city);
        WeatherData weatherData = weatherDataController.weatherDataForStationBeforeTime(station, timestamp);
        float temperature = weatherData.getAirtemperature();
        String phenomenon = weatherData.getPhenomenon();
        float windspeed = weatherData.getWindspeed();

        if (!allowedVehicles.contains(vehicle)) {
            throw new VehicleTypeNotFoundException();
        }
        float cost = rbfController.RBFForCityAndVehicle(city, vehicle);
        cost += atefController.ATEFForVehicleAndTemperature(vehicle, temperature);
        float wsef = wsefController.WSEFForVehicleAndWindspeed(vehicle, windspeed);
        float wpef = wpefController.WPEFForVehicleAndPhenomenon(vehicle, phenomenon);
        if (wsef == -1 || wpef == -1) {
            throw new ForbiddenVehicleTypeException();
        }
        cost += wsef + wpef;

        return String.format("%.2f€", cost);
    }

    @GetMapping("/deliveryfee/{city}/{vehicle}")
    private String totalDeliveryFee(@PathVariable String city, @PathVariable String vehicle) {
        return totalDeliveryFee(city, vehicle, LocalDateTime.now());
    }
}
