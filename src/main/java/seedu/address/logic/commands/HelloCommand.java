package seedu.address.logic.commands;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * A command that displays a personalized hello message.
 * This command is part of the tutorial demonstration.
 */
public class HelloCommand extends Command {
    public static final String COMMAND_WORD = "hello";
    public static final String MESSAGE_SUCCESS = Messages.MESSAGE_HELLO;
    public static final String MESSAGE_SUCCESS_WITH_NAME = Messages.MESSAGE_HELLO_WITH_NAME;
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays a greeting message.\n"
            + "Parameters: [NAME] (optional)\n"
            + "Example: " + COMMAND_WORD + " John Doe";

    private final String name;

    /**
     * Creates a HelloCommand with default greeting.
     */
    public HelloCommand() {
        this.name = null;
    }

    /**
     * Creates a HelloCommand with a personalized greeting.
     * @param name The name to be included in the greeting
     */
    public HelloCommand(String name) {
        this.name = name;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (name == null) {
            return new CommandResult(MESSAGE_SUCCESS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS_WITH_NAME, name));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof HelloCommand)) {
            return false;
        }
        HelloCommand otherHelloCommand = (HelloCommand) other;
        
        if (this.name == null) {
            return otherHelloCommand.name == null;
        }
        return this.name.equals(otherHelloCommand.name);
    }
}
