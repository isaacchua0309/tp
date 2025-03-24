package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.group.Group;

/**
 * Adds a group in the address book.
 */
public class AddGroupCommand extends Command {

    public static final String COMMAND_WORD = "addgroup";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new group to the address book.\n"
            + "Example: " + COMMAND_WORD + " g/TeamAlpha";

    public static final String MESSAGE_SUCCESS = "New group added: %1$s";
    public static final String MESSAGE_DUPLICATE_GROUP = "This group already exists in the address book";

    private final String groupName;

    public AddGroupCommand(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (model.hasGroup(groupName)) {
            throw new CommandException(MESSAGE_DUPLICATE_GROUP);
        }

        Group group = new Group(groupName);
        model.addGroup(group);
        return new CommandResult(String.format(MESSAGE_SUCCESS, groupName));
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof AddGroupCommand
                && ((AddGroupCommand) other).groupName.equals(this.groupName);
    }
}
