package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import martinrahe.fujitsu_internship_test_deliveryprice.errors.RBFNotFoundException;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RBFController {
    private final RBFRepository repository;

    RBFController(RBFRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds the RBF object in the database that corresponds to our city and vehicle type.
     * Returns the rbf value of that object.
     * Throws an exception if such an object does not exist.
     * @param city the name of the city we want the base fee for
     * @param vehicle the type of vehicle we want the base fee for
     * @return the base fee value in floating point format
     */
    public float RBFForCityAndVehicle(String city, String vehicle) {
        List<RBF> rbfList = repository.findByCityAndVehicle(city, vehicle);
        if (rbfList.size() == 0) {
            throw new RBFNotFoundException();
        }
        return rbfList.get(0).getRbf();
    }
}
