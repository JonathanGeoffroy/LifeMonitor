package lifemonitor.application.model.jsonMapping;

/**
 * Representation of a pharmacy location.
 * @author Célia Cacciatore, Quentin Bailleul
 */
public class PharmacyPlace {

    // Pharmacy name
    private String name;

    // Pharmacy location
    private double latitude;
    private double longitude;

    /**
     * Pharmacy location
     * @param latitude latitude of pharmacy
     * @param longitude longitude of pharmacy
     * @param name name of pharmacy
     */
    public PharmacyPlace(double latitude, double longitude, String name) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
