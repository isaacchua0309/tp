package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class SportListTest {

    @Test
    public void constructor_empty_returnsEmptySportList() {
        SportList sportList = new SportList();
        assertTrue(sportList.isEmpty());
        assertEquals(0, sportList.size());
    }

    @Test
    public void constructor_nonEmpty_returnsSportListWithSports() {
        List<Sport> sports = Arrays.asList(new Sport("soccer"), new Sport("basketball"));
        SportList sportList = new SportList(sports);
        assertEquals(2, sportList.size());
        assertFalse(sportList.isEmpty());
    }

    @Test
    public void add_validSport_success() {
        SportList sportList = new SportList();
        sportList.add(new Sport("soccer"));
        assertEquals(1, sportList.size());
        assertTrue(sportList.contains(new Sport("soccer")));
    }

    @Test
    public void remove_existingSport_success() {
        SportList sportList = new SportList();
        Sport sport = new Sport("soccer");
        sportList.add(sport);
        assertTrue(sportList.remove(sport));
        assertEquals(0, sportList.size());
    }

    @Test
    public void remove_nonExistingSport_returnsFalse() {
        SportList sportList = new SportList();
        assertFalse(sportList.remove(new Sport("soccer")));
    }

    @Test
    public void asUnmodifiableList_modifyList_throwsUnsupportedOperationException() {
        SportList sportList = new SportList();
        sportList.add(new Sport("soccer"));
        List<Sport> unmodifiableList = sportList.asUnmodifiableList();

        try {
            unmodifiableList.add(new Sport("basketball"));
            throw new AssertionError("The operation should have thrown an exception");
        } catch (UnsupportedOperationException e) {
            // Expected exception, test passes
        }
    }

    @Test
    public void copy_returnsCopyWithSameElements() {
        SportList original = new SportList();
        original.add(new Sport("soccer"));

        SportList copy = original.copy();
        assertEquals(original.size(), copy.size());
        assertTrue(copy.contains(new Sport("soccer")));


        copy.add(new Sport("basketball"));
        assertEquals(1, original.size());
        assertEquals(2, copy.size());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        SportList sportList = new SportList();
        assertTrue(sportList.equals(sportList));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        SportList sportList = new SportList();
        assertFalse(sportList.equals("string"));
    }

    @Test
    public void equals_sameSports_returnsTrue() {
        SportList sportList1 = new SportList();
        sportList1.add(new Sport("soccer"));

        SportList sportList2 = new SportList();
        sportList2.add(new Sport("soccer"));

        assertTrue(sportList1.equals(sportList2));
    }

    @Test
    public void equals_differentSports_returnsFalse() {
        SportList sportList1 = new SportList();
        sportList1.add(new Sport("soccer"));

        SportList sportList2 = new SportList();
        sportList2.add(new Sport("basketball"));

        assertFalse(sportList1.equals(sportList2));
    }

    @Test
    public void toString_nonEmptyList_returnsFormattedString() {
        SportList sportList = new SportList();
        sportList.add(new Sport("soccer"));
        sportList.add(new Sport("basketball"));

        assertEquals("soccer, basketball", sportList.toString());
    }
}
