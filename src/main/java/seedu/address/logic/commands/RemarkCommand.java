package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Remark;

/**
 * Adds or updates the remark of an existing person in the address book.
 */
public class RemarkCommand extends Command {

    public static final String COMMAND_WORD = "remark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds or updates the remark of the person identified "
            + "by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer) r/REMARK\n"
            + "Example: " + COMMAND_WORD + " 1 r/Met at a conference.";

    public static final String MESSAGE_REMARK_PERSON_SUCCESS = "Updated remark for person: %1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";

    private final int index;
    private final Remark remark;

    /**
     * Creates a RemarkCommand to update the remark of a person.
     *
     * @param index of the person in the filtered person list whose remark is to be updated.
     * @param remark new remark for the person.
     */
    public RemarkCommand(int index, Remark remark) {
        requireNonNull(remark);
        this.index = index;
        this.remark = remark;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index < 0 || index >= model.getFilteredPersonList().size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = model.getFilteredPersonList().get(index);
        Person editedPerson = createEditedPerson(personToEdit, remark);
        model.setPerson(personToEdit, editedPerson);

        return new CommandResult(String.format(MESSAGE_REMARK_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with an updated remark.
     *
     * @param personToEdit The person whose remark is to be updated.
     * @param remark The new remark.
     * @return A {@code Person} with the updated remark.
     */
    private static Person createEditedPerson(Person personToEdit, Remark remark) {
        // Assuming that Person has a constructor that accepts name, phone, email, address, remark, and tags.
        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), remark, personToEdit.getTags());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemarkCommand)) {
            return false;
        }

        RemarkCommand otherCommand = (RemarkCommand) other;
        return index == otherCommand.index && remark.equals(otherCommand.remark);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("remark", remark)
                .toString();
    }
}
