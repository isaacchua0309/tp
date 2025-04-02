package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportList;

/**
 * Deletes a sport from an existing contact in the address book.
 */
public class DeleteSportCommand extends Command {

    public static final String COMMAND_WORD = "deletesport";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a sport from the contact identified by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) s/SPORT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 s/Badminton";
    public static final String MESSAGE_DELETE_SPORT_SUCCESS = "Successfully removed %1$s from %2$s's sports list!";
    public static final String MESSAGE_NO_SPORT_FOUND = "%1$s is not listed \n"
            + "as a sport for %2$s. Please check the current sports list for this contact.";

    public static final String MESSAGE_CANNOT_DELETE_SPORT = "%1$s cannot be removed \n"
            + "as %2$s must have at least one sport. Try adding a new sport before removing this one.";

    private final Index targetIndex;
    private final Sport sport;

    /**
     * Creates a DeleteSportCommand to delete the specified sport from the person at the given index.
     *
     * @param targetIndex The index of the person in the filtered person list.
     * @param sport The sport to be deleted.
     */
    public DeleteSportCommand(Index targetIndex, Sport sport) {
        requireNonNull(sport);
        this.targetIndex = targetIndex;
        this.sport = sport;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve the filtered person list
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException("Invalid person index");
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        // Check if the sport is present
        if (!personToEdit.getSports().contains(sport)) {
            throw new CommandException(String.format(MESSAGE_NO_SPORT_FOUND, sport, personToEdit.getName().fullName));
        }

        // Get the SportList and remove the sport to be deleted
        SportList updatedSports = personToEdit.getSportList();
        if (updatedSports.size() == 1) {
            throw new CommandException(String.format(
                    MESSAGE_CANNOT_DELETE_SPORT, sport, personToEdit.getName().fullName));
        }
        updatedSports.remove(sport);

        // Create a new Person with the updated sports list
        Person editedPerson = createEditedPerson(personToEdit, updatedSports);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_DELETE_SPORT_SUCCESS,
        sport.toString(), personToEdit.getName().fullName));
    }

    /**
     * Creates and returns a {@code Person} with an updated SportList.
     *
     * @param personToEdit The person to be updated.
     * @param updatedSports The new SportList.
     * @return A new Person object with the updated sports.
     */
    private static Person createEditedPerson(Person personToEdit, SportList updatedSports) {
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getPostalCode(),
                personToEdit.getTags(),
                updatedSports);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof DeleteSportCommand)) {
            return false;
        }
        DeleteSportCommand otherCommand = (DeleteSportCommand) other;
        return targetIndex.equals(otherCommand.targetIndex) && sport.equals(otherCommand.sport);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("sport", sport)
                .toString();
    }
}
