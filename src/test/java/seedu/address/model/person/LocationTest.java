package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LocationTest {
    private static final String VALID_POSTAL_CODE_1 = "018906";
    private static final String VALID_POSTAL_CODE_2 = "018935";
    private static final Address VALID_ADDRESS_1 = new Address("1 STRAITS BOULEVARD SINGAPORE CHINESE CULTURAL CENTRE");
    private static final Address VALID_ADDRESS_2 = new Address("5 STRAITS VIEW THE HEART SINGAPORE");
    private static final double VALID_LATITUDE_1 = 1.2789;
    private static final double VALID_LONGITUDE_1 = 103.8537;
    private static final double VALID_LATITUDE_2 = 1.2823;
    private static final double VALID_LONGITUDE_2 = 103.8528;

    @Test
    public void constructor_nullPostalCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(null, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1));
    }

    @Test
    public void constructor_nullAddress_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Location(VALID_POSTAL_CODE_1, null,
                VALID_LATITUDE_1, VALID_LONGITUDE_1));
    }

    @Test
    public void constructor_validInputs_createsLocation() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertNotNull(location);
        assertEquals(VALID_POSTAL_CODE_1, location.getPostalCode());
        assertEquals(VALID_ADDRESS_1, location.getAddress());
        assertEquals(VALID_LATITUDE_1, location.getLatitude());
        assertEquals(VALID_LONGITUDE_1, location.getLongitude());
    }

    @Test
    public void getPostalCode_returnsCorrectPostalCode() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(VALID_POSTAL_CODE_1, location.getPostalCode());
    }

    @Test
    public void getAddress_returnsCorrectAddress() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(VALID_ADDRESS_1, location.getAddress());
    }

    @Test
    public void getLatitude_returnsCorrectLatitude() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(VALID_LATITUDE_1, location.getLatitude());
    }

    @Test
    public void getLongitude_returnsCorrectLongitude() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(VALID_LONGITUDE_1, location.getLongitude());
    }

    @Test
    public void distanceTo_sameLocation_returnsZero() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(0.0, location.distanceTo(location), 0.001);
    }

    @Test
    public void distanceTo_differentLocation_returnsCorrectDistance() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_2, VALID_ADDRESS_2,
                VALID_LATITUDE_2, VALID_LONGITUDE_2);



        double expectedDistance = 0.384;
        assertEquals(expectedDistance, location1.distanceTo(location2), 0.05);
    }

    @Test
    public void distanceTo_commutative_equalsDistance() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_2, VALID_ADDRESS_2,
                VALID_LATITUDE_2, VALID_LONGITUDE_2);

        assertEquals(location1.distanceTo(location2), location2.distanceTo(location1), 0.001);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertTrue(location.equals(location));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertFalse(location.equals(null));
    }

    @Test
    public void equals_differentObjectType_returnsFalse() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertFalse(location.equals("String"));
    }

    @Test
    public void equals_sameValues_returnsTrue() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertTrue(location1.equals(location2));
    }

    @Test
    public void equals_differentPostalCode_returnsFalse() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_2, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertFalse(location1.equals(location2));
    }

    @Test
    public void equals_differentAddress_returnsFalse() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_2,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertFalse(location1.equals(location2));
    }

    @Test
    public void equals_differentLatitude_returnsFalse() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_2, VALID_LONGITUDE_1);

        assertFalse(location1.equals(location2));
    }

    @Test
    public void equals_differentLongitude_returnsFalse() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_2);

        assertFalse(location1.equals(location2));
    }

    @Test
    public void hashCode_sameObject_sameHashCode() {
        Location location = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(location.hashCode(), location.hashCode());
    }

    @Test
    public void hashCode_equalObjects_sameHashCode() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        assertEquals(location1.hashCode(), location2.hashCode());
    }

    @Test
    public void hashCode_differentObjects_differentHashCode() {
        Location location1 = new Location(VALID_POSTAL_CODE_1, VALID_ADDRESS_1,
                VALID_LATITUDE_1, VALID_LONGITUDE_1);

        Location location2 = new Location(VALID_POSTAL_CODE_2, VALID_ADDRESS_2,
                VALID_LATITUDE_2, VALID_LONGITUDE_2);

        assertNotEquals(location1.hashCode(), location2.hashCode());
    }
}
