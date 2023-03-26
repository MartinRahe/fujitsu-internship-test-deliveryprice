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

    /**
     * Stores the given WeatherData object in the database.
     * @param weatherData a WeatherData object to be stored in the database
     */
    public void saveWeatherData(WeatherData weatherData)  {
        repository.save(weatherData);
    }

    /**
     * Finds the newest WeatherData object in the database that was recorder before the given timestamp by the given station.
     * Throws an exception if such an object does not exist.
     * @param station the name of the weather station we want data from
     * @param timestamp the unix timestamp we want our data to be older than
     * @return a Weatherdata object
     */
    public WeatherData weatherDataForStationBeforeTime(String station, long timestamp) {
        for (WeatherData weatherData : repository.findByNameOrderByTimestampDesc(station)) {
            if (weatherData.getTimestamp() <= timestamp) {
                return weatherData;
            }
        }
        throw new WeatherDataNotFoundException();
    }
}
