package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ATEFRepository extends JpaRepository<ATEF, Long> {

    List<ATEF> findByVehicleOrderByMaxtempAsc(String vehicle);
}
