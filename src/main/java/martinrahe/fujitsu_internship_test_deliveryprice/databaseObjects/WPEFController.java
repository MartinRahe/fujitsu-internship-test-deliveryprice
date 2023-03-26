package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WPEFController {
    private final WPEFRepository repository;

    WPEFController(WPEFRepository repository) {
        this.repository = repository;
    }

    public float WPEFForVehicleAndPhenomenon(String vehicle, String phenomenon) {
        if (phenomenon.isBlank()) {
            return 0;
        }
        List<WPEF> wpefList = repository.findByVehicle(vehicle);
        for (WPEF wpef: wpefList) {
            if (phenomenon.contains(wpef.getPhenomenon())) {
                return wpef.getWpef();
            }
        }
        return 0;
    }
}
