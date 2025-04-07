package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;

/**
 * Deletes a sport from the global sports list by index.
 */
public class DeleteSportCommand extends Command {

    public static final String COMMAND_WORD = "deletesport";

    public static final String MESSAGE_USAGE_GLOBAL = COMMAND_WORD
            + ": Deletes the sport at the specified INDEX from the global sports list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_USAGE_PERSON = COMMAND_WORD
            + ": Deletes the specified sport at the specified INDEX from the person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 s/basketball";

    public static final String MESSAGE_DELETE_SPORT_SUCCESS_GLOBAL = "Deleted Sport: %1$s from global list";
    public static final String MESSAGE_DELETE_SPORT_SUCCESS_PERSON = "Deleted the sport %1$s for %2$s";
    public static final String MESSAGE_INVALID_SPORT_INDEX = "The sport index provided is invalid";

    public static final String MESSAGE_CANNOT_DELETE_SPORT = "The sport %1$s, cannot be deleted as %2$s has to "
            + "have at least 1 sport";
    public static final String MESSAGE_NO_SPORT_FOUND = "The sport %1$s, does not exist for %2$s";

    private final Index targetIndex;
    private final Path filePath;

    private final Sport sport;

    /**
     * Default constructor for deletion of global sport that uses the file path from UserPrefs.
     *
     * @param targetIndex The index of the sport to delete.
     */
    public DeleteSportCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.filePath = null; // Will use the path from UserPrefs during execution
        this.sport = null;
    }

    /**
     * Constructor for deletion of global sport with configurable file path for testing.
     *
     * @param targetIndex The index of the sport to delete.
     * @param filePath The custom file path to save sports to.
     */
    public DeleteSportCommand(Index targetIndex, Path filePath) {
        requireNonNull(targetIndex);
        requireNonNull(filePath);
        this.targetIndex = targetIndex;
        this.filePath = filePath;
        this.sport = null;
    }

    /**
     * Constructor for deletion of sport for specified person
     */
    public DeleteSportCommand(Index targetIndex, Sport sport) {
        requireNonNull(targetIndex);
        requireNonNull(sport);
        this.targetIndex = targetIndex;
        this.filePath = null;
        this.sport = sport;

    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the list of valid sports
        List<String> sortedSports = Sport.getSortedValidSports();

        if (targetIndex.getZeroBased() >= sortedSports.size()) {
            throw new CommandException(MESSAGE_INVALID_SPORT_INDEX);
        }

        String sportToDelete = sortedSports.get(targetIndex.getZeroBased());

        // Delete the sport from the global list
        Sport.deleteValidSport(targetIndex.getZeroBased());

        // Remove this sport from all people who have it
        List<Person> allPersons = model.getFilteredPersonList();
        for (Person person : allPersons) {
            Sport sport = new Sport(sportToDelete);
            if (person.getSports().contains(sport)) {
                List<Sport> updatedSports = new ArrayList<>(person.getSports());
                updatedSports.remove(sport);
                Person editedPerson = createEditedPerson(person, updatedSports);
                model.setPerson(person, editedPerson);
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SPORT_SUCCESS_GLOBAL, sportToDelete));
    }

    /**
     * Creates and returns a {@code Person} with an updated list of sports.
     *
     * @param personToEdit The person to be updated.
     * @param updatedSports The new list of sports.
     * @return A new Person object with the updated sports.
     */
    private static Person createEditedPerson(Person personToEdit, List<Sport> updatedSports) {
        return new Person(
                personToEdit.getName(),
                personToEdit.getPhone(),
                personToEdit.getEmail(),
                personToEdit.getAddress(),
                personToEdit.getTags(),
                updatedSports);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) { // short circuit if same objec
            return true;
        }
        // check for equality in the event same object
        boolean filePathEquality = true;
        boolean sportEquality = true;
        if (other instanceof DeleteSportCommand) {
            DeleteSportCommand otherCommand = (DeleteSportCommand) other;
            filePathEquality = Objects.equals(filePath, otherCommand.filePath);
            sportEquality = Objects.equals(sport, otherCommand.sport);
        }

        return (other instanceof DeleteSportCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteSportCommand) other).targetIndex)
                && filePathEquality
                && sportEquality); // state check
    }
}
