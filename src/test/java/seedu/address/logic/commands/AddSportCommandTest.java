package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportList;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSportCommand}.
 */
public class AddSportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullSport_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Sport sportToAdd = new Sport("cricket");
        AddSportCommand addSportCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), sportToAdd);

        String expectedMessage = String.format(AddSportCommand.MESSAGE_SUCCESS,
                sportToAdd.toString(), personToModify.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person modifiedPerson = createPersonWithAdditionalSport(personToModify, sportToAdd);
        expectedModel.setPerson(personToModify, modifiedPerson);

        assertEquals(addSportCommand.execute(model), new CommandResult(expectedMessage));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_duplicateSport_throwsCommandException() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Sport sportToAdd = new Sport("soccer");

        // First add a spor
        Person modifiedPerson = createPersonWithAdditionalSport(personToModify, sportToAdd);
        model.setPerson(personToModify, modifiedPerson);

        // Try to add the same sport again
        AddSportCommand addSportCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), sportToAdd);
        assertThrows(CommandException.class,
                String.format(AddSportCommand.MESSAGE_DUPLICATE_SPORT, sportToAdd), () -> addSportCommand.execute(model));
    }

    @Test
    public void execute_invalidSport_throwsCommandException() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_OBJECT.getZeroBased());
        Sport invalidSport = new Sport("swimming"); // Not in the valid sports lis
        AddSportCommand addSportCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), invalidSport);

        assertThrows(CommandException.class,
                Sport.getMessageConstraints(), () -> addSportCommand.execute(model));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int outOfBoundIndex = model.getFilteredPersonList().size();
        AddSportCommand addSportCommand = new AddSportCommand(outOfBoundIndex, new Sport("soccer"));

        assertThrows(CommandException.class,
                "Invalid person index", () -> addSportCommand.execute(model));
    }

    @Test
    public void equals() {
        Sport soccer = new Sport("soccer");
        Sport basketball = new Sport("basketball");
        AddSportCommand addSoccerCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), soccer);
        AddSportCommand addBasketballCommand = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), basketball);
        AddSportCommand addSoccerSecondCommand = new AddSportCommand(INDEX_SECOND_OBJECT.getZeroBased(), soccer);

        // same object -> returns true
        assertTrue(addSoccerCommand.equals(addSoccerCommand));

        // same values -> returns true
        AddSportCommand addSoccerCommandCopy = new AddSportCommand(INDEX_FIRST_OBJECT.getZeroBased(), soccer);
        assertTrue(addSoccerCommand.equals(addSoccerCommandCopy));

        // different types -> returns false
        assertFalse(addSoccerCommand.equals(1));

        // null -> returns false
        assertFalse(addSoccerCommand.equals(null));

        // different sport -> returns false
        assertFalse(addSoccerCommand.equals(addBasketballCommand));

        // different index -> returns false
        assertFalse(addSoccerCommand.equals(addSoccerSecondCommand));
    }

    /**
     * Creates a new person with all the same fields as the original, plus an additional sport.
     */
    private Person createPersonWithAdditionalSport(Person person, Sport sport) {
        SportList updatedSports = person.getSportList();
        updatedSports.add(sport);

        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getPostalCode(),
                person.getTags(),
                updatedSports);
    }
}
