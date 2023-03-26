package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WSEFController {
    private final WSEFRepository repository;

    WSEFController(WSEFRepository repository) {
        this.repository = repository;
    }

    /**
     * Finds the WSEF object in the database that corresponds to our vehicle type
     * and has the highest minspeed value that is lower than our wind speed.
     * Returns the wsef value of that object.
     * Returns 0 if such an object does not exist.
     * @param vehicle the type of vehicle we want the extra fee for
     * @param windspeed the wind speed we want the extra fee at
     * @return extra fee value in floating point format
     */
    public float WSEFForVehicleAndWindspeed(String vehicle, float windspeed) {
        List<WSEF> wsefList = repository.findByVehicleOrderByMinspeedDesc(vehicle);
        for (WSEF wsef: wsefList) {
            if (windspeed > wsef.getMinspeed()) {
                return wsef.getWsef();
            }
        }
        return 0;
    }
}
