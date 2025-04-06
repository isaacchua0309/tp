package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;


/**
 * Contains tests for the {@code AddCommand} that adds a person to the address book.
 */
public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }



    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_NAME, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Person alice = new PersonBuilder().withName("Alice").build();
        Person bob = new PersonBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);


        assertTrue(addAliceCommand.equals(addAliceCommand));


        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));


        assertFalse(addAliceCommand.equals(1));


        assertFalse(addAliceCommand.equals(null));


        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(ALICE);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + ALICE + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A concrete Model stub that implements the Model interface.
     * This stub supports person-related operations for testing the AddCommand.
     */
    private class ModelStub implements Model {
        protected final ArrayList<Person> personsAdded = new ArrayList<>();


        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {

        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            return new UserPrefs();
        }

        @Override
        public GuiSettings getGuiSettings() {
            return new GuiSettings();
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {

        }

        @Override
        public Path getAddressBookFilePath() {
            return Path.of("dummy/path");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {

        }


        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {

        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }


        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(p -> p.isSamePerson(person));
        }

        @Override
        public void deletePerson(Person target) {
            personsAdded.remove(target);
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            requireNonNull(target);
            requireNonNull(editedPerson);
            int index = personsAdded.indexOf(target);
            if (index != -1) {
                personsAdded.set(index, editedPerson);
            }
        }

        @Override
        public int isPersonUnique(String name) {
            requireNonNull(name);
            long count = personsAdded.stream()
                    .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                    .count();

            return (int) count;
        }

        @Override
        public Person getPerson(String name) {
            requireNonNull(name);
            List<Person> matched = personsAdded.stream()
                    .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                    .toList();

            if (matched.size() == 1) {
                return matched.get(0);
            } else if (matched.isEmpty()) {
                throw new IllegalArgumentException("No person found with the name: " + name);
            } else {
                throw new IllegalArgumentException("Multiple persons found with the name: " + name);
            }
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return FXCollections.observableArrayList(personsAdded);
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            requireNonNull(predicate);

        }

        @Override
        public void sortFilteredPersonListByDistance(Location location) {
            requireNonNull(location);

        }


        @Override
        public void sortFilteredPersonListAlphabetically() {
            throw new AssertionError("This method should not be called.");
        }
        public ObservableList<Game> getFilteredGameList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void updateFilteredGameList(Predicate<Game> predicate) {
            requireNonNull(predicate);

        }

        public void setGame(Game target, Game editedGame) {
            requireNonNull(target);
            requireNonNull(editedGame);
        }


        @Override
        public ObservableList<Game> getGameList() {
            return FXCollections.observableArrayList();
        }

        @Override
        public void addGame(Game game) {

        }

        @Override
        public boolean hasGame(Game game) {
            return false;
        }

        @Override
        public void deleteGame(Game target) {

        }
    }


    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
            personsAdded.add(person);
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accepts the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {


        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(p -> p.isSamePerson(person));
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
