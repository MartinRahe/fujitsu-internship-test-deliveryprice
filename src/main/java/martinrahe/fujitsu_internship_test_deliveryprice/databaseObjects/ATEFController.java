package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ATEFController {
    private final ATEFRepository repository;

    ATEFController(ATEFRepository repository) {
        this.repository = repository;
    }

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
