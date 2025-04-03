package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportList;

/**
 * Adds a sport to an existing contact in the address book.
 */
public class AddSportCommand extends Command {

    public static final String COMMAND_WORD = "addsport";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a sport to the contact identified "
            + "by the index number used in the displayed person list. "
            + "Parameters: INDEX (must be a positive integer) s/SPORT_NAME\n"
            + "Example: " + COMMAND_WORD + " 1 s/Badminton";
    public static final String MESSAGE_SUCCESS = "Successfully added %1$s to %2$s's sports list!";
    public static final String MESSAGE_DUPLICATE_SPORT = "This sport is already \n"
            + "in %s's sport list. Each sport can only be added once per contact.";

    private final int index;
    private final Sport sport;

    /**
     * Creates an AddSportCommand to add the specified sport to the person at the given index.
     *
     * @param index The index of the person in the filtered person list.
     * @param sport The sport to be added.
     */
    public AddSportCommand(int index, Sport sport) {
        requireNonNull(sport);
        this.index = index;
        this.sport = sport;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Retrieve the filtered person lis
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index < 0 || index >= lastShownList.size()) {
            throw new CommandException("Invalid person index");
        }

        Person personToEdit = lastShownList.get(index);

        // Check if the sport is already presen
        if (personToEdit.getSports().contains(sport)) {
            throw new CommandException(MESSAGE_DUPLICATE_SPORT);
        }

        // Check if the sport is valid
        String trimmedSport = sport.toString().toLowerCase();
        if (!Sport.isValidSport(trimmedSport)) {
            throw new CommandException(Sport.getMessageConstraints());
        }

        // Create a new SportList including the new spor
        SportList updatedSports = personToEdit.getSportList();
        updatedSports.add(sport);

        // Create a new Person with the updated sports lis
        Person editedPerson = createEditedPerson(personToEdit, updatedSports);
        model.setPerson(personToEdit, editedPerson);
        return new CommandResult(String.format(MESSAGE_SUCCESS, sport.toString(), personToEdit.getName().fullName));
    }

    /**
     * Creates and returns a {@code Person} with an updated list of sports.
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
        // short circuit if same objec
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof AddSportCommand otherCommand)) {
            return false;
        }
        return index == otherCommand.index && sport.equals(otherCommand.sport);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("sport", sport)
                .toString();
    }
}
