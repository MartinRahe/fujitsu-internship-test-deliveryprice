package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import martinrahe.fujitsu_internship_test_deliveryprice.errors.WeatherDataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class WeatherDataController {
    private final WeatherDataRepository repository;

    WeatherDataController(WeatherDataRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/weatherdata")
    List<WeatherData> all() {
        return repository.findAll();
    }

    public void saveWeatherData(WeatherData weatherData)  {
        repository.save(weatherData);
    }


    public WeatherData weatherDataForStationBeforeTime(String station, long timestamp) {
        for (WeatherData weatherData : repository.findByNameOrderByTimestampDesc(station)) {
            if (weatherData.getTimestamp() <= timestamp) {
                return weatherData;
            }
        }
        throw new WeatherDataNotFoundException();
    }
}
