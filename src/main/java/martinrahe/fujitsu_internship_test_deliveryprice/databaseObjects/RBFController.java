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

    public float RBFForCityAndVehicle(String city, String vehicle) {
        List<RBF> rbfList = repository.findByCityAndVehicle(city, vehicle);
        if (rbfList.size() == 0) {
            throw new RBFNotFoundException();
        }
        return rbfList.get(0).getRbf();
    }
}
