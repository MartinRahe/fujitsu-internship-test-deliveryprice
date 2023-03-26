package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WPEFController {
    private final WPEFRepository repository;

    WPEFController(WPEFRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds all WPEF objects in the database.
     * If our weather phenomenon is empty, returns 0.
     * Otherwise finds the first WPEF object in the list which has a phenomenon contained in our current one.
     * Returns the wpef value of that object.
     * Returns 0 if such an object does not exist.
     * @param vehicle the type of vehicle we want the extra fee for
     * @param phenomenon the type of weather phenomenon we want the extra fee for
     * @return extra fee value in floating point format
     */
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
