package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RBF {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String vehicle;

    @Column(nullable = false)
    private float rbf;

    public String getCity() {
        return this.city;
    }

    public String getVehicle() {
        return this.vehicle;
    }

    public float getRbf() {
        return this.rbf;
    }
}
