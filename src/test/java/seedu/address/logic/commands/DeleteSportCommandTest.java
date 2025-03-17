package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;

public class DeleteSportCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    // Ensures sports exist for deletion
    @BeforeEach
    public void setUp() {
        Person personToMutate = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        // Since each Person must have 1 sport, we do this to ensure that after deletion, invariant not violated
        Person personToModify = createPersonWithTwoSports(personToMutate);
        model.setPerson(personToMutate, personToModify);
        assertEquals(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()), personToModify);
    }
    @Test
    public void execute_validIndexUnfilteredList_success() throws CommandException {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Sport sportToDelete = new Sport("badminton");

        DeleteSportCommand deleteSportCommand = new DeleteSportCommand(INDEX_FIRST_PERSON, sportToDelete);

        String expectedMessage = String.format(DeleteSportCommand.MESSAGE_DELETE_SPORT_SUCCESS,
                sportToDelete, personToModify.getName().fullName);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person modifiedPerson = createPersonWithLessSport(personToModify, sportToDelete);
        expectedModel.setPerson(personToModify, modifiedPerson);
        assertEquals(deleteSportCommand.execute(model), new CommandResult(expectedMessage));
        assertEquals(expectedModel, model);
    }

    @Test
    public void execute_noSportFound_throwsCommandException() {
        Person personToModify = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Sport sportToDelete = personToModify.getSports().get(0);

        // First delete existing sport
        Person modifiedPerson = createPersonWithLessSport(personToModify, sportToDelete);
        model.setPerson(personToModify, modifiedPerson);

        // Try to delete same sport again
        DeleteSportCommand deleteSportCommand = new DeleteSportCommand(INDEX_FIRST_PERSON, sportToDelete);

        String expectedMessage = String.format(DeleteSportCommand.MESSAGE_NO_SPORT_FOUND, sportToDelete,
                modifiedPerson.getName().fullName);
        assertThrows(CommandException.class, expectedMessage, (
            ) -> deleteSportCommand.execute(model));
    }
    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromZeroBased(model.getFilteredPersonList().size() + 1);
        DeleteSportCommand deleteSportCommand = new DeleteSportCommand(outOfBoundIndex, new Sport("soccer"));

        assertThrows(CommandException.class,
                "Invalid person index", () -> deleteSportCommand.execute(model));
    }

    @Test
    public void equals() {
        Sport soccer = new Sport("soccer");
        Sport badminton = new Sport("badminton");
        DeleteSportCommand deleteSoccerCommand = new DeleteSportCommand(INDEX_FIRST_PERSON, soccer);
        DeleteSportCommand deleteBadmintonCommand = new DeleteSportCommand(INDEX_SECOND_PERSON, badminton);
        DeleteSportCommand deleteBadmintonSecondCommand = new DeleteSportCommand(INDEX_FIRST_PERSON, badminton);

        // same object -> returns true
        assertTrue(deleteSoccerCommand.equals(deleteSoccerCommand));

        // same values -> returns true
        DeleteSportCommand deleteSoccerCommandCopy = new DeleteSportCommand(INDEX_FIRST_PERSON, soccer);
        assertTrue(deleteSoccerCommand.equals(deleteSoccerCommandCopy));

        // different types -> returns false
        assertFalse(deleteSoccerCommand.equals(1));

        // null -> returns false
        assertFalse(deleteSoccerCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteBadmintonCommand.equals(deleteBadmintonSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        Sport sport = model.getFilteredPersonList().get(0).getSports().get(0);
        DeleteSportCommand deleteSportCommand = new DeleteSportCommand(targetIndex, sport);
        String expected = DeleteSportCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + ", sport="
                + sport + "}";
        assertEquals(expected, deleteSportCommand.toString());
    }

    /**
     * Creates a new person with all the same fields as the original, except for sports field which is pre-defined.
     */
    private Person createPersonWithTwoSports(Person person) {
        List<Sport> updatedSports = new ArrayList<>(Arrays.asList(new Sport("soccer"),
                new Sport("badminton")));

        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTags(),
                updatedSports);
    }

    /**
     * Creates a new person with all the same fields as the original, less one sport.
     */
    private Person createPersonWithLessSport(Person person, Sport sport) {
        List<Sport> updatedSports = new ArrayList<>(person.getSports());
        updatedSports.remove(sport);

        return new Person(
                person.getName(),
                person.getPhone(),
                person.getEmail(),
                person.getAddress(),
                person.getTags(),
                updatedSports);
    }
}
