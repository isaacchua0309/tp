package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {

        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null));


        assertFalse(Email.isValidEmail(""));
        assertFalse(Email.isValidEmail(" "));


        assertFalse(Email.isValidEmail("@example.com"));
        assertFalse(Email.isValidEmail("peterjackexample.com"));
        assertFalse(Email.isValidEmail("peterjack@"));


        assertFalse(Email.isValidEmail("peterjack@-"));
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com"));
        assertFalse(Email.isValidEmail("peter jack@example.com"));
        assertFalse(Email.isValidEmail("peterjack@exam ple.com"));
        assertFalse(Email.isValidEmail(" peterjack@example.com"));
        assertFalse(Email.isValidEmail("peterjack@example.com "));
        assertFalse(Email.isValidEmail("peterjack@@example.com"));
        assertFalse(Email.isValidEmail("peter@jack@example.com"));
        assertFalse(Email.isValidEmail("-peterjack@example.com"));
        assertFalse(Email.isValidEmail("peterjack-@example.com"));
        assertFalse(Email.isValidEmail("peter..jack@example.com"));
        assertFalse(Email.isValidEmail("peterjack@example@com"));
        assertFalse(Email.isValidEmail("peterjack@.example.com"));
        assertFalse(Email.isValidEmail("peterjack@example.com."));
        assertFalse(Email.isValidEmail("peterjack@-example.com"));
        assertFalse(Email.isValidEmail("peterjack@example.com-"));
        assertFalse(Email.isValidEmail("peterjack@example.c"));


        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com"));
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com"));
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com"));
        assertTrue(Email.isValidEmail("a@bc"));
        assertTrue(Email.isValidEmail("test@localhost"));
        assertTrue(Email.isValidEmail("123@145"));
        assertTrue(Email.isValidEmail("a1+be.d@example1.com"));
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com"));
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu"));
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");


        assertTrue(email.equals(new Email("valid@email")));


        assertTrue(email.equals(email));


        assertFalse(email.equals(null));


        assertFalse(email.equals(5.0f));


        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
