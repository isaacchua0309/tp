package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

/**
 * Contains integration tests for HelpCommand with the LogicManager.
 */
public class HelpWindowIntegrationTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data");
    private final JsonAddressBookStorage addressBookStorage =
            new JsonAddressBookStorage(TEST_DATA_FOLDER.resolve("addressBook.json"));
    private final JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(TEST_DATA_FOLDER.resolve("userPrefs.json"));
    private final StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
    private final Model model = new ModelManager();
    private final LogicManager logic = new LogicManager(model, storage);

    /**
     * Tests that the help command is correctly parsed and executed by the LogicManager
     */
    @Test
    public void execute_helpCommand_success() throws CommandException, ParseException {

        CommandResult commandResult = logic.execute("help");
        assertEquals(SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());


        commandResult = logic.execute("   help   ");
        assertEquals(SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());


        commandResult = logic.execute("help some extra arguments");
        assertEquals(SHOWING_HELP_MESSAGE, commandResult.getFeedbackToUser());
        assertTrue(commandResult.isShowHelp());
        assertFalse(commandResult.isExit());
    }

    /**
     * This test is disabled because it requires JavaFX initialization
     */
    @Test
    @Disabled("Requires JavaFX initialization")
    public void helpWindow_containsAllImplementedCommands() {

    }

    /**
     * Tests that invalid commands are properly rejected
     */
    @Test
    public void execute_invalidCommand_parseException() {
        try {
            logic.execute("helpme");
            throw new AssertionError("Expected ParseException was not thrown");
        } catch (Exception e) {
            assertEquals(MESSAGE_UNKNOWN_COMMAND, e.getMessage());
        }
    }
}
