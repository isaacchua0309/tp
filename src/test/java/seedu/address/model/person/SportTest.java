package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SportTest {

    @Test
    public void constructor_validSport_success() {
        // Valid sport names
        Sport sport = new Sport("basketball");
        assertNotNull(sport);
        assertEquals("basketball", sport.sportName);

        sport = new Sport("Tennis");
        assertNotNull(sport);
        assertEquals("Tennis", sport.sportName);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sport(null));
    }

    @Test
    public void isValidSport_validSports_returnsTrue() {
        // Valid sports from the VALID_SPORTS set
        assertTrue(Sport.isValidSport("soccer"));
        assertTrue(Sport.isValidSport("basketball"));
        assertTrue(Sport.isValidSport("tennis"));
        assertTrue(Sport.isValidSport("badminton"));
        assertTrue(Sport.isValidSport("cricket"));
    }

    @Test
    public void isValidSport_validSportsWithDifferentCase_returnsTrue() {
        // Valid sports with different letter cases
        assertTrue(Sport.isValidSport("SOCCER"));
        assertTrue(Sport.isValidSport("Basketball"));
        assertTrue(Sport.isValidSport("Tennis"));
    }

    @Test
    public void isValidSport_validSportsWithWhitespace_returnsTrue() {
        // Valid sports with leading/trailing spaces
        assertTrue(Sport.isValidSport(" soccer "));
        assertTrue(Sport.isValidSport("basketball "));
        assertTrue(Sport.isValidSport(" tennis"));
    }

    @Test
    public void isValidSport_invalidSports_returnsFalse() {
        // Invalid sports not in the VALID_SPORTS set
        assertFalse(Sport.isValidSport("swimming"));
        assertFalse(Sport.isValidSport("cycling"));
        assertFalse(Sport.isValidSport("martial arts"));
    }

    @Test
    public void isValidSport_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> Sport.isValidSport(null));
    }

    @Test
    public void getNormalizedName_returnsLowercase() {
        assertEquals("soccer", new Sport("Soccer").getNormalizedName());
        assertEquals("basketball", new Sport("BASKETBALL").getNormalizedName());
        assertEquals("tennis", new Sport("Tennis").getNormalizedName());
    }

    @Test
    public void toString_validSport_returnsSportName() {
        Sport sport = new Sport("Soccer");
        assertEquals("Soccer", sport.toString());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        Sport sport = new Sport("Badminton");
        assertTrue(sport.equals(sport));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        Sport firstSport = new Sport("Tennis");
        Sport secondSport = new Sport("Tennis");
        assertTrue(firstSport.equals(secondSport));
    }

    @Test
    public void equals_sameSportDifferentCase_returnsTrue() {
        Sport firstSport = new Sport("tennis");
        Sport secondSport = new Sport("Tennis");
        assertTrue(firstSport.equals(secondSport));
    }

    @Test
    public void equals_nullObject_returnsFalse() {
        Sport sport = new Sport("Hockey");
        assertFalse(sport.equals(null));
    }

    @Test
    public void equals_differentClass_returnsFalse() {
        Sport sport = new Sport("Cricket");
        assertFalse(sport.equals("Cricket")); // Compare with a String
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        Sport firstSport = new Sport("Golf");
        Sport secondSport = new Sport("Rugby");
        assertFalse(firstSport.equals(secondSport));
    }

    @Test
    public void hashCode_sameValueDifferentCase_sameHashCode() {
        Sport firstSport = new Sport("tennis");
        Sport secondSport = new Sport("Tennis");
        assertEquals(firstSport.hashCode(), secondSport.hashCode());
    }

    @Test
    public void hashCode_differentValues_differentHashCodes() {
        Sport firstSport = new Sport("tennis");
        Sport secondSport = new Sport("soccer");
        assertFalse(firstSport.hashCode() == secondSport.hashCode());
    }
}
