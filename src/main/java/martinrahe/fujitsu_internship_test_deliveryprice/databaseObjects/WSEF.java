package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WSEF {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String vehicle;

    @Column(nullable = false)
    private float minspeed;

    @Column(nullable = false)
    private float wsef;

    public float getMinspeed() {
        return this.minspeed;
    }

    public float getWsef() {
        return this.wsef;
    }
}
