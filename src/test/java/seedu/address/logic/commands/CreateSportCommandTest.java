package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Sport;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) for {@code CreateSportCommand}.
 */
public class CreateSportCommandTest {

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
    public void constructor_nullSportName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreateSportCommand(null));
    }

    @Test
    public void execute_newSport_success() throws Exception {
        // Create a sport that doesn't exist in the default lis
        String newSportName = "archery";

        // Create command with default constructor (will use UserPrefs path)
        CreateSportCommand command = new CreateSportCommand(newSportName);

        // Save the current set of valid sports to restore later
        Set<String> originalSports = Sport.getValidSports();

        try {
            // Execute the command
            CommandResult result = command.execute(model);

            // Verify the result message
            assertEquals(String.format(CreateSportCommand.MESSAGE_SUCCESS, newSportName), result.getFeedbackToUser());

            // Verify the sport was added to the valid sports se
            assertTrue(Sport.isValidSport(newSportName));

            // Verify the sport was saved to the file path from UserPrefs
            assertTrue(Files.exists(testSportsFile));
        } finally {
            // Restore original valid sports
            Sport.loadDefaultSports();
        }
    }

    @Test
    public void execute_withCustomFilePath_success() throws Exception {
        // Create a sport that doesn't exist in the default lis
        String newSportName = "archery";
        Path customPath = testFolder.resolve("customSportList.json");

        // Create command with custom file path
        CreateSportCommand command = new CreateSportCommand(newSportName, customPath);

        try {
            // Execute the command
            CommandResult result = command.execute(model);

            // Verify the result message
            assertEquals(String.format(CreateSportCommand.MESSAGE_SUCCESS, newSportName), result.getFeedbackToUser());

            // Verify the sport was added to the valid sports se
            assertTrue(Sport.isValidSport(newSportName));

            // Verify the sport was saved to the custom file path
            assertTrue(Files.exists(customPath));
        } finally {
            // Restore original valid sports
            Sport.loadDefaultSports();
        }
    }

    @Test
    public void execute_duplicateSport_throwsCommandException() {
        // Use a sport that already exists in the default lis
        String existingSportName = "soccer";
        CreateSportCommand command = new CreateSportCommand(existingSportName);

        assertThrows(CommandException.class, CreateSportCommand.MESSAGE_DUPLICATE_SPORT, () ->
                command.execute(model));
    }

    @Test
    public void execute_caseInsensitivity_throwsCommandException() {
        // Use a sport that already exists but with different case
        String existingSportName = "SoCcEr"; // "soccer" exists in default lis
        CreateSportCommand command = new CreateSportCommand(existingSportName);

        assertThrows(CommandException.class, CreateSportCommand.MESSAGE_DUPLICATE_SPORT, () ->
                command.execute(model));
    }

    @Test
    public void equals() {
        CreateSportCommand addArcheryCommand = new CreateSportCommand("archery");
        CreateSportCommand addSwimmingCommand = new CreateSportCommand("swimming");

        // same object -> returns true
        assertTrue(addArcheryCommand.equals(addArcheryCommand));

        // same values -> returns true
        CreateSportCommand addArcheryCommandCopy = new CreateSportCommand("archery");
        assertTrue(addArcheryCommand.equals(addArcheryCommandCopy));

        // different types -> returns false
        assertFalse(addArcheryCommand.equals(1));

        // null -> returns false
        assertFalse(addArcheryCommand.equals(null));

        // different sport -> returns false
        assertFalse(addArcheryCommand.equals(addSwimmingCommand));
    }
}
