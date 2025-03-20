package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Location;

public class LocationUtilTest {

    private static final String VALID_POSTAL_CODE = "018906";
    private static final String INVALID_POSTAL_CODE = "999999"; // Assuming this is invalid
    private static final String EMPTY_POSTAL_CODE = "";
    private static final String MALFORMED_POSTAL_CODE = "1234AB";
    private static final String POSTAL_CODE_TOO_SHORT = "12345";
    private static final String POSTAL_CODE_TOO_LONG = "1234567";

    private static final Address VALID_ADDRESS = new Address("1 STRAITS BOULEVARD SINGAPORE CHINESE CULTURAL CENTRE");
    private static final Address ANOTHER_VALID_ADDRESS = new Address("5 STRAITS VIEW THE HEART SINGAPORE");

    /**
     * Tests and prints the time taken to load the LocationUtil class (including JSON loading).
     */
    @Test
    public void testJsonLoadTime() throws ClassNotFoundException {
        long loadStart = System.nanoTime();
        // Force the class to load, triggering the static block which loads the JSON.
        Class.forName("seedu.address.commons.util.LocationUtil");
        long loadEnd = System.nanoTime();
        long loadTime = loadEnd - loadStart;
        System.out.println("Time to load LocationUtil (including JSON loading): " + loadTime + " ns");
        // Optionally, assert that loading takes less than a predefined threshold.
        assertTrue(loadTime < 5_000_000_000L, "JSON loading took too long: " + loadTime + " ns");
    }

    /**
     * Tests the performance of creating Location objects.
     */
    @Test
    public void testLocationCreationPerformance() {
        // Example user input.
        Address userAddress = new Address("5 STRAITS VIEW THE HEART SINGAPORE 018935");
        String postalCode = "018935";

        // Warm-up: Create one Location to ensure any caching or static initialization is complete.
        Location warmupLocation = LocationUtil.createLocation(userAddress, postalCode);
        System.out.println("Warm-up Location: " + warmupLocation);

        final int iterations = 100_000;
        long creationStart = System.nanoTime();
        for (int i = 0; i < iterations; i++) {
            Location loc = LocationUtil.createLocation(userAddress, postalCode);
        }
        long creationEnd = System.nanoTime();
        long totalCreationTime = creationEnd - creationStart;
        double avgCreationTime = totalCreationTime / (double) iterations;

        System.out.println("Created " + iterations + " Location objects in " + totalCreationTime + " ns.");
        System.out.println("Average time per creation: " + avgCreationTime + " ns.");

        // Optionally, assert that the average creation time is below a desired threshold.
        assertTrue(avgCreationTime < 100_000, "Average creation time is too high: " + avgCreationTime + " ns.");
    }

    @Test
    public void testIsValidPostalCode_validPostalCode_returnsTrue() {
        assertTrue(LocationUtil.isValidPostalCode(VALID_POSTAL_CODE));
    }

    @Test
    public void testIsValidPostalCode_invalidPostalCode_returnsFalse() {
        assertFalse(LocationUtil.isValidPostalCode(INVALID_POSTAL_CODE));
    }

    @Test
    public void testIsValidPostalCode_emptyPostalCode_returnsFalse() {
        assertFalse(LocationUtil.isValidPostalCode(EMPTY_POSTAL_CODE));
    }

    @Test
    public void testIsValidPostalCode_malformedPostalCode_returnsFalse() {
        assertFalse(LocationUtil.isValidPostalCode(MALFORMED_POSTAL_CODE));
    }

    @Test
    public void testIsValidPostalCode_postalCodeTooShort_returnsFalse() {
        assertFalse(LocationUtil.isValidPostalCode(POSTAL_CODE_TOO_SHORT));
    }

    @Test
    public void testIsValidPostalCode_postalCodeTooLong_returnsFalse() {
        assertFalse(LocationUtil.isValidPostalCode(POSTAL_CODE_TOO_LONG));
    }

    @Test
    public void testCreateLocation_validInputs_createsLocation() {
        Location location = LocationUtil.createLocation(VALID_ADDRESS, VALID_POSTAL_CODE);

        assertNotNull(location);
        assertTrue(location instanceof Location);
        assertTrue(location.getPostalCode().equals(VALID_POSTAL_CODE));
        assertTrue(location.getAddress().equals(VALID_ADDRESS));
    }

    @Test
    public void testCreateLocation_nullAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LocationUtil.createLocation(null, VALID_POSTAL_CODE));
    }

    @Test
    public void testCreateLocation_nullPostalCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> LocationUtil.createLocation(VALID_ADDRESS, null));
    }

    @Test
    public void testCreateLocation_invalidPostalCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationUtil.createLocation(VALID_ADDRESS, INVALID_POSTAL_CODE));
    }

    @Test
    public void testCreateLocation_emptyPostalCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationUtil.createLocation(VALID_ADDRESS, EMPTY_POSTAL_CODE));
    }

    @Test
    public void testCreateLocation_malformedPostalCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationUtil.createLocation(VALID_ADDRESS, MALFORMED_POSTAL_CODE));
    }

    @Test
    public void testCreateLocation_postalCodeTooShort_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationUtil.createLocation(VALID_ADDRESS, POSTAL_CODE_TOO_SHORT));
    }

    @Test
    public void testCreateLocation_postalCodeTooLong_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                LocationUtil.createLocation(VALID_ADDRESS, POSTAL_CODE_TOO_LONG));
    }

    @Test
    public void testCreateLocation_differentAddressSamePostalCode_createsCorrectLocation() {
        Location location1 = LocationUtil.createLocation(VALID_ADDRESS, VALID_POSTAL_CODE);
        Location location2 = LocationUtil.createLocation(ANOTHER_VALID_ADDRESS, VALID_POSTAL_CODE);

        // Since the postal code is the same, the latitude and longitude should be the same
        assertTrue(location1.getLatitude() == location2.getLatitude());
        assertTrue(location1.getLongitude() == location2.getLongitude());
    }
}
