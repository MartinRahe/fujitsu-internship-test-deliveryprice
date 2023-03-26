package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ATEFController {
    private final ATEFRepository repository;

    ATEFController(ATEFRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds the ATEF object in the database that corresponds to our vehicle type
     * and has the lowest maxtemp value that is higher than our temperature.
     * Returns the atef value of that object.
     * Returns 0 if such an object does not exist.
     * @param vehicle the type of vehicle we want the extra fee for
     * @param temperature the air temperature we want the extra fee at
     * @return extra fee value in floating point format
     */
    public float ATEFForVehicleAndTemperature(String vehicle, float temperature) {
        List<ATEF> atefList = repository.findByVehicleOrderByMaxtempAsc(vehicle);
        for (ATEF atef: atefList) {
            if (temperature < atef.getMaxtemp()) {
                return atef.getAtef();
            }
        }
        return 0;
    }
}
