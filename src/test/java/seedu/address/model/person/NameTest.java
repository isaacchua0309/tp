package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {

        assertThrows(NullPointerException.class, () -> Name.isValidName(null));


        assertFalse(Name.isValidName(""));
        assertFalse(Name.isValidName(" "));
        assertFalse(Name.isValidName("^"));
        assertFalse(Name.isValidName("peter*"));


        assertTrue(Name.isValidName("peter jack"));
        assertTrue(Name.isValidName("12345"));
        assertTrue(Name.isValidName("peter the 2nd"));
        assertTrue(Name.isValidName("Capital Tan"));
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd"));
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");


        assertTrue(name.equals(new Name("Valid Name")));


        assertTrue(name.equals(name));


        assertFalse(name.equals(null));


        assertFalse(name.equals(5.0f));


        assertFalse(name.equals(new Name("Other Valid Name")));
    }
}
