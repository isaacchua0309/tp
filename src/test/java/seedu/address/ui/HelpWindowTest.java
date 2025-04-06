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
    private static final String USERGUIDE_URL = "https://github.com/AY2324-CS2103T-W10-4/tp/blob/"
            + "master/docs/UserGuide.md";
    private static final String HELP_WINDOW_TITLE = "Help";



    /**
     * This is a simple test to ensure HelpCommand returns a CommandResult that triggers
     * the help window to open.
     */
    @Test
    public void helpCommand_execute_returnsCorrectCommandResult() throws Exception {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(null);

        assertEquals(HelpCommand.SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
        assertFalse(result.isExit());
    }
}

