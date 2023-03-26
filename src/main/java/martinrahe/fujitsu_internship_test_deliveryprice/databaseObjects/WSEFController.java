package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WSEFController {
    private final WSEFRepository repository;

    WSEFController(WSEFRepository repository) {
        this.repository = repository;
    }

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
