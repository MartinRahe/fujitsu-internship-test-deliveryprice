package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WSEFRepository extends JpaRepository<WSEF, Long> {

    List<WSEF> findByVehicleOrderByMinspeedDesc(String vehicle);
}
