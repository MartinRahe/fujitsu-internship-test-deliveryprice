package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RBFRepository extends JpaRepository<RBF, Long> {

    List<RBF> findByCityAndVehicle(String city, String vehicle);
}
