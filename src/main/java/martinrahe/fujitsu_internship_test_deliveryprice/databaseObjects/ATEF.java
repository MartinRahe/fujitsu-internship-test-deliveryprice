package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ATEF {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String vehicle;

    @Column(nullable = false)
    private float maxtemp;

    @Column(nullable = false)
    private float atef;

    public float getMaxtemp() {
        return this.maxtemp;
    }

    public float getAtef() {
        return this.atef;
    }
}
