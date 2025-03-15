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
        Sport sport = new Sport("Basketball");
        assertNotNull(sport);
        assertEquals("Basketball", sport.sportName);

        sport = new Sport("Tennis");
        assertNotNull(sport);
        assertEquals("Tennis", sport.sportName);
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sport(null));
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
}
