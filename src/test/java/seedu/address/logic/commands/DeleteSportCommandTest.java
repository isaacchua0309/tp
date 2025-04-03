package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Sport;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code DeleteSportCommand}.
 */
public class DeleteSportCommandTest {

    @TempDir
    public Path testFolder;

    private Path testSportsFile;
    private Model model;
    private UserPrefs userPrefs;

    @BeforeEach
    public void setUp() throws IOException {
        // Ensure we have a clean test environment with the temporary directory
        testSportsFile = testFolder.resolve("testGlobalSportList.json");

        // Setup custom UserPrefs with test file path
        userPrefs = new UserPrefs();
        userPrefs.setGlobalSportsListFilePath(testSportsFile);

        // Load default sports for testing
        Sport.loadDefaultSports();

        model = new ModelManager(TypicalPersons.getTypicalAddressBook(), userPrefs);
    }

    @AfterEach
    public void tearDown() {
        // Reset to default sports
        Sport.loadDefaultSports();
    }

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DeleteSportCommand(null));
    }

    @Test
    public void execute_validIndex_success() throws Exception {
        // Get the sorted list to ensure consistent indexing
        List<String> sortedSports = Sport.getSortedValidSports();
        String sportToDelete = sortedSports.get(0); // First sport in the alphabetically sorted lis

        DeleteSportCommand command = new DeleteSportCommand(Index.fromZeroBased(0));

        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteSportCommand.MESSAGE_DELETE_SPORT_SUCCESS_GLOBAL, sportToDelete),
                result.getFeedbackToUser());

        // Verify the sport was deleted
        assertFalse(Sport.isValidSport(sportToDelete));
    }

    @Test
    public void execute_withCustomFilePath_success() throws Exception {
        // Get the sorted list to ensure consistent indexing
        List<String> sortedSports = Sport.getSortedValidSports();
        String sportToDelete = sortedSports.get(0); // First sport in the alphabetically sorted lis

        Path customPath = testFolder.resolve("customSportList.json");
        DeleteSportCommand command = new DeleteSportCommand(Index.fromZeroBased(0), customPath);

        CommandResult result = command.execute(model);

        assertEquals(String.format(DeleteSportCommand.MESSAGE_DELETE_SPORT_SUCCESS_GLOBAL, sportToDelete),
                result.getFeedbackToUser());

        // Verify the sport was deleted
        assertFalse(Sport.isValidSport(sportToDelete));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        int size = Sport.getSortedValidSports().size();
        DeleteSportCommand command = new DeleteSportCommand(Index.fromZeroBased(size));

        assertThrows(CommandException.class, DeleteSportCommand.MESSAGE_INVALID_SPORT_INDEX, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        DeleteSportCommand firstCommand = new DeleteSportCommand(Index.fromOneBased(1));
        DeleteSportCommand secondCommand = new DeleteSportCommand(Index.fromOneBased(2));

        // same object -> returns true
        assertTrue(firstCommand.equals(firstCommand));

        // same values -> returns true
        DeleteSportCommand firstCommandCopy = new DeleteSportCommand(Index.fromOneBased(1));
        assertTrue(firstCommand.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(firstCommand.equals(1));

        // null -> returns false
        assertFalse(firstCommand.equals(null));

        // different index -> returns false
        assertFalse(firstCommand.equals(secondCommand));
    }
}
