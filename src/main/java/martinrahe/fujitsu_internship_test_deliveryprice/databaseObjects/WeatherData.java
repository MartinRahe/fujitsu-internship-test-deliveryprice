package martinrahe.fujitsu_internship_test_deliveryprice.databaseObjects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class WeatherData {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    private int wmocode;
    private String phenomenon;
    private float airtemperature;
    private float windspeed;

    @Column(nullable = false)
    private long timestamp;

    public WeatherData () {}

    public WeatherData (String name, int wmocode, String phenomenon, float airtemperature, float windspeed, long timestamp) {
        this.name = name;
        this.wmocode = wmocode;
        this.phenomenon = phenomenon;
        this.airtemperature = airtemperature;
        this.windspeed = windspeed;
        this.timestamp = timestamp;
    }

    public String getName() {
        return this.name;
    }

    public int getWMOCode() {
        return this.wmocode;
    }

    public String getPhenomenon() {
        return this.phenomenon;
    }

    public float getAirtemperature() {
        return this.airtemperature;
    }

    public float getWindspeed() {
        return this.windspeed;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
