package seedu.address.commons.util;

/**
 * A helper class that holds raw location data loaded from the JSON file.
 */
public class RawLocationData {
    private String address;
    private double latitude;
    private double longitude;

    // Getters are needed for Jackson conversion.
    public String getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
