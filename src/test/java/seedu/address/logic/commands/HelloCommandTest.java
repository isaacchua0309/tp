package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class HelloCommandTest {
    @Test
    public void execute_helloCommand_success() throws CommandException {
        Model model = new ModelManager();
        HelloCommand helloCommand = new HelloCommand();
        assertEquals(helloCommand.execute(model).getFeedbackToUser(), HelloCommand.MESSAGE_SUCCESS);
    }
} 