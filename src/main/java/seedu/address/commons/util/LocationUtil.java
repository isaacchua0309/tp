package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.person.Location;

/**
 * A factory class to create {@code Location} objects using user input and a JSON file
 * mapping postal codes to location data.
 */
public class LocationUtil {

    // Cached mapping from postal codes to their corresponding raw location data.
    private static final Map<String, RawLocationData> locationData = new HashMap<>();

    // Path to the JSON file. Adjust the file path as necessary.
    private static final Path LOCATION_DATA_FILE =
            Paths.get("data", "postal_code_data.json");

    static {
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
     * @param userInputAddress the address entered by the user.
     * @param postalCode the postal code entered by the user.
     * @return a new {@code Location} object with corresponding latitude and longitude.
     * @throws IllegalArgumentException if the postal code is not found.
     */
    public static Location createLocation(String userInputAddress, String postalCode) {
        requireAllNonNull(userInputAddress, postalCode);
        requireNonNull(locationData, "Location data has not been loaded");

        RawLocationData rawData = locationData.get(postalCode);
        if (rawData == null) {
            throw new IllegalArgumentException("Postal code not found: " + postalCode);
        }

        // Optional: Validate if the user-input address matches rawData.getAddress() if necessary.
        // For now, we use the user input for the address.

        return new Location(postalCode, userInputAddress, rawData.getLatitude(), rawData.getLongitude());
    }
}
