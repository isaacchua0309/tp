package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command that displays a hello message.
 */
public class HelloCommand extends Command {
    public static final String COMMAND_WORD = "hello";
    public static final String MESSAGE_SUCCESS = "Hello from AB3!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult(MESSAGE_SUCCESS);
    }
} 