package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

/**
 * Contains integration tests (interaction with the Model) and unit tests for HelpCommand.
 */
public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }

    @Test
    public void execute_help_returnsCorrectCommandResult() throws CommandException {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(model);

        // Verify the command result has the correct properties
        assertEquals(SHOWING_HELP_MESSAGE, result.getFeedbackToUser());
        assertTrue(result.isShowHelp());
        assertFalse(result.isExit());
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        HelpCommand helpCommand = new HelpCommand();
        assertTrue(helpCommand.equals(helpCommand));
    }

    @Test
    public void equals_differentType_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.equals("string"));
    }

    @Test
    public void equals_null_returnsFalse() {
        HelpCommand helpCommand = new HelpCommand();
        assertFalse(helpCommand.equals(null));
    }

    @Test
    public void equals_sameType_returnsTrue() {
        HelpCommand helpCommand1 = new HelpCommand();
        HelpCommand helpCommand2 = new HelpCommand();
        assertTrue(helpCommand1.equals(helpCommand2));
    }

    @Test
    public void helpCommand_messageUsageIsCorrect() {
        String expectedUsage = "help: Shows program usage instructions.\nExample: help";
        assertEquals(expectedUsage, HelpCommand.MESSAGE_USAGE);
    }

    @Test
    public void helpCommand_commandWordIsCorrect() {
        assertEquals("help", HelpCommand.COMMAND_WORD);
    }

    /**
     * Integration test to verify that the model state remains unchanged after executing help command
     */
    @Test
    public void execute_helpCommand_modelUnchanged() throws CommandException {
        Model originalModel = new ModelManager();
        Model testModel = new ModelManager();

        // Execute the help command
        new HelpCommand().execute(testModel);

        // Verify the model state remains unchanged
        assertEquals(originalModel, testModel);
    }
}


