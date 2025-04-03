package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

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
        // Reset to default sports to ensure test consistency
        Sport.loadDefaultSports();

        // Valid sports from the default se
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
        // Invalid sports not in the VALID_SPORTS se
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

    @Test
    public void getSortedValidSports_returnsAlphabeticalOrder() {
        // Reset to default sports
        Sport.loadDefaultSports();

        List<String> sortedSports = Sport.getSortedValidSports();

        // Check that the list is sorted alphabetically
        List<String> expectedOrder = List.of(
            "badminton", "baseball", "basketball", "cricket", "golf",
            "hockey", "rugby", "soccer", "tennis", "volleyball"
        );

        assertEquals(expectedOrder, sortedSports);
    }

    @Test
    public void deleteValidSport_validIndex_removesCorrectSport() {
        // Reset to default sports
        Sport.loadDefaultSports();

        // Get the sorted list to determine the index
        List<String> sortedSports = Sport.getSortedValidSports();
        int indexOfCricket = sortedSports.indexOf("cricket");

        // Delete the spor
        String deletedSport = Sport.deleteValidSport(indexOfCricket);

        // Verify the returned sport name
        assertEquals("cricket", deletedSport);

        // Verify the sport was removed
        assertFalse(Sport.isValidSport("cricket"));

        // Verify the size of the list decreased
        assertEquals(9, Sport.getValidSports().size());
    }

    @Test
    public void deleteValidSport_invalidIndex_returnsNull() {
        // Reset to default sports
        Sport.loadDefaultSports();

        // Get the size of the list to use an invalid index
        int size = Sport.getSortedValidSports().size();

        // Try to delete a sport with an invalid index
        String deletedSport = Sport.deleteValidSport(size);

        // Verify null is returned
        assertNull(deletedSport);

        // Verify the list remains unchanged
        assertEquals(10, Sport.getValidSports().size());

        // Try negative index
        deletedSport = Sport.deleteValidSport(-1);

        // Verify null is returned
        assertNull(deletedSport);

        // Verify the list remains unchanged
        assertEquals(10, Sport.getValidSports().size());
    }
}

