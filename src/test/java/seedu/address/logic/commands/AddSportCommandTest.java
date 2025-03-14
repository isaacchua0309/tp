package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;

/**
 * Contains integration tests (interaction with the Model) for {@code AddSportCommand}.
 */
public class AddSportCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullSport_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), null));
    }

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Sport sportToAdd = new Sport("soccer");
        AddSportCommand addSportCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), sportToAdd);

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
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Sport sportToAdd = new Sport("soccer");
        
        // First add a sport
        Person modifiedPerson = createPersonWithAdditionalSport(personToModify, sportToAdd);
        model.setPerson(personToModify, modifiedPerson);
        
        // Try to add the same sport again
        AddSportCommand addSportCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), sportToAdd);
        assertThrows(CommandException.class, 
                AddSportCommand.MESSAGE_DUPLICATE_SPORT, () -> addSportCommand.execute(model));
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
        AddSportCommand addSoccerCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), soccer);
        AddSportCommand addBasketballCommand = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), basketball);
        AddSportCommand addSoccerSecondCommand = new AddSportCommand(INDEX_SECOND_PERSON.getZeroBased(), soccer);

        // same object -> returns true
        assertTrue(addSoccerCommand.equals(addSoccerCommand));

        // same values -> returns true
        AddSportCommand addSoccerCommandCopy = new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), soccer);
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
        List<Sport> updatedSports = new ArrayList<>(person.getSports());
        updatedSports.add(sport);
        
        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTags(),
                updatedSports);
    }
} 