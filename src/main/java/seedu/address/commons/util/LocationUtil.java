package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Location;


/**
 * A factory class to create {@code Location} objects using user input and a JSON file
 * mapping postal codes to location data.
 * <p>
 * This utility manages the creation of Location objects, which serve as the authoritative
 * source for postal code information in the application. This design ensures consistent
 * postal code validation and access.
 * <p>
 * When handling postal codes, always use the methods in this class for validation
 * and the getPostalCode() method from Location objects for retrieval.
 */
public class LocationUtil {

    // Cached mapping from postal codes to their corresponding raw location data.
    private static final Map<String, RawLocationData> locationData = new HashMap<>();

    // Path to the JSON file. Adjust the file path as necessary.
    private static final Path LOCATION_DATA_FILE;

    static {
        try {
            URL resource = LocationUtil.class.getClassLoader().getResource("locationdata/postal_code_data.json");
            requireNonNull(resource, "Resource not found");
            LOCATION_DATA_FILE = Paths.get(resource.toURI());

        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        try {
            loadLocationData();
        } catch (DataLoadingException e) {
            // Handle the error appropriately in your application.
            e.printStackTrace();
            // Optionally, rethrow as an unchecked exception.
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Loads the postal code data from the JSON file using JsonUtil.
     *
     * @throws DataLoadingException if loading the JSON file fails.
     */
    private static void loadLocationData() throws DataLoadingException {
        try {
            // Read the JSON file into a string.
            String jsonString = Files.readString(LOCATION_DATA_FILE);
            // Deserialize into a raw Map.
            Map<?, ?> rawMap = JsonUtil.fromJsonString(jsonString, Map.class);

            // Use a default ObjectMapper to convert each map entry into a RawLocationData instance.
            ObjectMapper mapper = new ObjectMapper();
            for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
                String postalCode = (String) entry.getKey();
                // Convert the inner LinkedHashMap to a RawLocationData object.
                RawLocationData data = mapper.convertValue(entry.getValue(), RawLocationData.class);
                locationData.put(postalCode, data);
            }
        } catch (IOException e) {
            throw new DataLoadingException(e);
        }
    }

    /**
     * Creates a {@code Location} object using the provided user address and postal code.
     *
     * @param address the address entered by the user.
     * @param postalCode the postal code entered by the user.
     * @return a new {@code Location} object with corresponding latitude and longitude.
     * @throws IllegalArgumentException if the postal code is not found.
     */
    public static Location createLocation(Address address, String postalCode) {
        requireAllNonNull(address, postalCode);
        requireNonNull(locationData, "Location data has not been loaded");

        RawLocationData rawData = locationData.get(postalCode);
        if (rawData == null) {
            throw new IllegalArgumentException("Postal code not found: " + postalCode);
        }

        // Optional: Validate if the user-input address matches rawData.getAddress() if necessary.
        // For now, we use the user input for the address.

        return new Location(postalCode, address, rawData.getLatitude(), rawData.getLongitude());
    }

    /**
     * Checks if the given postal code exists in the location data.
     * Use this method to validate postal codes before creating Location objects.
     *
     * @param postalCode the postal code to validate
     * @return true if the postal code exists in the location data, false otherwise
     */
    public static boolean isValidPostalCode(String postalCode) {
        return locationData.containsKey(postalCode);
    }
}
