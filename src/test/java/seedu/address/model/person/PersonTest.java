package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTAL_CODE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, all other attributes different -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB)
                .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns false
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertFalse(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns false
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertFalse(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different postal code -> returns false
        editedAlice = new PersonBuilder(ALICE).withPostalCode(VALID_POSTAL_CODE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }

    @Test
    public void toStringMethod() {
        String expected = Person.class.getCanonicalName() + "{name=" + ALICE.getName() + ", phone=" + ALICE.getPhone()
                + ", email=" + ALICE.getEmail() + ", address=" + ALICE.getAddress()
                + ", postalCode=" + ALICE.getPostalCode()
                + ", tags=" + ALICE.getTags()
                + ", sports=" + ALICE.getSports() + "}";
        assertEquals(expected, ALICE.toString());
    }

    @Test
    public void getSports_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getSports().remove(0));
    }

    @Test
    public void hasSport_personWithNoSports_returnsFalse() {
        Person person = new PersonBuilder().build();
        Sport sport = new Sport("basketball");
        assertFalse(person.getSports().contains(sport));
    }

    @Test
    public void hasSport_personWithSport_returnsTrue() {
        Person person = new PersonBuilder().withSport("soccer").build();
        assertTrue(person.getSports().contains(new Sport("soccer")));
    }

    @Test
    public void addSport_success() {
        Person originalPerson = new PersonBuilder().build();
        List<Sport> updatedSports = new ArrayList<>(originalPerson.getSports());
        updatedSports.add(new Sport("tennis"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getTags(),
                updatedSports);

        assertTrue(updatedPerson.getSports().contains(new Sport("tennis")));
        assertEquals(originalPerson.getSports().size() + 1, updatedPerson.getSports().size());
    }

    @Test
    public void deleteSport_success() {
        Person originalPerson = new PersonBuilder().withSports("volleyball", "cricket").build();
        List<Sport> updatedSports = new ArrayList<>(originalPerson.getSports());
        updatedSports.remove(new Sport("cricket"));

        Person updatedPerson = new Person(
                originalPerson.getName(),
                originalPerson.getPhone(),
                originalPerson.getEmail(),
                originalPerson.getAddress(),
                originalPerson.getTags(),
                updatedSports);

        assertFalse(updatedPerson.getSports().contains(new Sport("cricket")));
        assertEquals(originalPerson.getSports().size() - 1, updatedPerson.getSports().size());
    }

    @Test
    public void constructor_nullPostalCode_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Person(
                new Name("Valid Name"),
                new Phone("12345678"),
                new Email("test@example.com"),
                new Address("Valid Address"),
                null, // null postal code
                new HashSet<>(),
                new ArrayList<>()));
    }

    @Test
    public void getPostalCode_returnsCorrectPostalCode() {
        String expectedPostalCode = "018906";
        Person person = new PersonBuilder().withPostalCode(expectedPostalCode).build();
        assertEquals(expectedPostalCode, person.getPostalCode());
    }

    @Test
    public void withDifferentPostalCode_createsNewPersonWithDifferentPostalCode() {
        String originalPostalCode = "018906";
        String newPostalCode = "018935";

        Person originalPerson = new PersonBuilder().withPostalCode(originalPostalCode).build();
        Person newPerson = new PersonBuilder(originalPerson).withPostalCode(newPostalCode).build();

        assertEquals(originalPostalCode, originalPerson.getPostalCode());
        assertEquals(newPostalCode, newPerson.getPostalCode());
        assertFalse(originalPerson.equals(newPerson));
    }

    @Test
    public void equals_samePostalCodeDifferentAddresses_returnsFalse() {
        String samePostalCode = "018906";

        Person person1 = new PersonBuilder()
                .withPostalCode(samePostalCode)
                .withAddress("1 Test Street")
                .build();

        Person person2 = new PersonBuilder()
                .withPostalCode(samePostalCode)
                .withAddress("2 Different Street")
                .build();

        assertFalse(person1.equals(person2));
    }

    @Test
    public void toString_includesPostalCode() {
        String postalCode = "018906";
        Person person = new PersonBuilder().withPostalCode(postalCode).build();

        // Test that toString contains the postal code information
        assertTrue(person.toString().contains(postalCode));
    }
}
