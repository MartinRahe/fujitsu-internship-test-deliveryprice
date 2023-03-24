package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
