package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SportTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Sport(null));
    }

    @Test
    public void equals() {
        Sport football = new Sport("Football");
        
        // same object -> returns true
        assertTrue(football.equals(football));
        
        // same values -> returns true
        Sport footballCopy = new Sport("Football");
        assertTrue(football.equals(footballCopy));
        
        // different types -> returns false
        assertFalse(football.equals(5));
        
        // null -> returns false
        assertFalse(football.equals(null));
        
        // different sport -> returns false
        Sport basketball = new Sport("Basketball");
        assertFalse(football.equals(basketball));
        
        // case insensitive comparison -> returns false (exact match required)
        Sport footballLowercase = new Sport("football");
        assertFalse(football.equals(footballLowercase));
    }

    @Test
    public void toString_returnsName() {
        Sport basketball = new Sport("Basketball");
        assertEquals("Basketball", basketball.toString());
    }
} 