package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Address;
import seedu.address.model.person.Location;

public class LocationUtilTest {

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
}
