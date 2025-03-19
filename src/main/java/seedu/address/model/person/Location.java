package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

/**
 * Represents a geographical location in Singapore.
 * It encapsulates postal code, address, latitude, and longitude information.
 * <p>
 * This class also provides functionality to calculate the distance between two locations.
 */
public class Location {

    private final String postalCode;
    private final Address address;
    private final double latitude;
    private final double longitude;

    /**
     * Constructs a {@code Location} with the specified details.
     *
     * @param postalCode the postal code.
     * @param address the full address.
     * @param latitude the latitude coordinate.
     * @param longitude the longitude coordinate.
     */
    public Location(String postalCode, Address address, double latitude, double longitude) {
        requireAllNonNull(postalCode, address);
        this.postalCode = postalCode;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address getAddress() {
        return address;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * Calculates the distance between this location and another location using the Haversine formula.
     *
     * @param other the other location to measure the distance to.
     * @return the distance in kilometers.
     */
    public double distanceTo(Location other) {
        Objects.requireNonNull(other, "Other location cannot be null");
        final int earthRadiusKm = 6371; // Earth's radius in kilometers
        double dLat = Math.toRadians(other.latitude - this.latitude);
        double dLon = Math.toRadians(other.longitude - this.longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(this.latitude))
                * Math.cos(Math.toRadians(other.latitude))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return earthRadiusKm * c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return Double.compare(location.latitude, latitude) == 0
                && Double.compare(location.longitude, longitude) == 0
                && postalCode.equals(location.postalCode)
                && address.equals(location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(postalCode, address, latitude, longitude);
    }

    @Override
    public String toString() {
        return String.format("Location[postalCode=%s, address=%s, latitude=%.6f, longitude=%.6f]",
                postalCode, address, latitude, longitude);
    }
}
