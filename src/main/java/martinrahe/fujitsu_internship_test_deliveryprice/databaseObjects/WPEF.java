package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WPEF {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String vehicle;

    @Column(nullable = false)
    private String phenomenon;

    @Column(nullable = false)
    private float wpef;
}
