package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Phone(invalidPhone));
    }

    @Test
    public void isValidPhone() {

        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));


        assertFalse(Phone.isValidPhone(""));
        assertFalse(Phone.isValidPhone(" "));
        assertFalse(Phone.isValidPhone("91"));
        assertFalse(Phone.isValidPhone("phone"));
        assertFalse(Phone.isValidPhone("9011p041"));
        assertFalse(Phone.isValidPhone("9312 1534"));


        assertTrue(Phone.isValidPhone("911"));
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123"));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");


        assertTrue(phone.equals(new Phone("999")));


        assertTrue(phone.equals(phone));


        assertFalse(phone.equals(null));


        assertFalse(phone.equals(5.0f));


        assertFalse(phone.equals(new Phone("995")));
    }
}
