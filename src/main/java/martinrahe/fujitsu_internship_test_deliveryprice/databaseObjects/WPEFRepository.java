package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WPEFRepository extends JpaRepository<WPEF, Long> {

    List<WPEF> findByVehicle(String vehicle);
}
