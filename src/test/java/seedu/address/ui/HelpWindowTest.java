package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.HelpCommand;

/**
 * Contains tests for HelpWindow.
 */
public class HelpWindowTest {
    private static final String USERGUIDE_URL = "https://se-education.org/addressbook-level3/UserGuide.html";
    private static final String HELP_WINDOW_TITLE = "Help";

    /**
     * This test is disabled because it requires JavaFX initialization
     */
    @Test
    @Disabled("Requires JavaFX initialization")
    public void constructor_validInputs_success() {
        // Disabled due to JavaFX initialization issues in testing environment
    }

    /**
     * This test is disabled because it requires JavaFX initialization
     */
    @Test
    @Disabled("Requires JavaFX initialization")
    public void isShowing_initialState_false() {
        // Disabled due to JavaFX initialization issues in testing environment
    }

    /**
     * This is a simple test to ensure HelpCommand returns a CommandResult that triggers
     * the help window to open.
     */
    @Test
    public void helpCommand_execute_returnsCorrectCommandResult() throws Exception {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(null); // Model not needed for this command

        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
        assertFalse(result.isExit());
    }
}

