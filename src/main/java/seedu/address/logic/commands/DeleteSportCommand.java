package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Sport;

/**
 * Deletes a sport from the global sports list by index.
 */
public class DeleteSportCommand extends Command {

    public static final String COMMAND_WORD = "deletesport";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the sport at the specified INDEX from the global sports list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SPORT_SUCCESS = "Deleted Sport: %1$s";
    public static final String MESSAGE_INVALID_SPORT_INDEX = "The sport index provided is invalid";

    private final Index targetIndex;
    private final Path filePath;

    /**
     * Default constructor that uses the file path from UserPrefs.
     *
     * @param targetIndex The index of the sport to delete.
     */
    public DeleteSportCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.filePath = null; // Will use the path from UserPrefs during execution
    }

    /**
     * Constructor with configurable file path for testing.
     *
     * @param targetIndex The index of the sport to delete.
     * @param filePath The custom file path to save sports to.
     */
    public DeleteSportCommand(Index targetIndex, Path filePath) {
        requireNonNull(targetIndex);
        requireNonNull(filePath);
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Get the sorted list of sports to ensure consistent indexing
        List<String> sortedSports = Sport.getSortedValidSports();

        if (targetIndex.getZeroBased() >= sortedSports.size()) {
            throw new CommandException(MESSAGE_INVALID_SPORT_INDEX);
        }

        // The actual deletion happens here
        String deletedSport = Sport.deleteValidSport(targetIndex.getZeroBased());

        if (deletedSport == null) {
            throw new CommandException(MESSAGE_INVALID_SPORT_INDEX);
        }

        try {
            // Use the provided file path or get it from UserPrefs if not provided
            Path pathToUse = filePath != null ? filePath : model.getUserPrefs().getGlobalSportsListFilePath();
            Sport.saveValidSports(pathToUse);
        } catch (IOException e) {
            throw new CommandException("Error saving sports to file: " + e.getMessage());
        }

        return new CommandResult(String.format(MESSAGE_DELETE_SPORT_SUCCESS, deletedSport));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same objec
                || (other instanceof DeleteSportCommand // instanceof handles nulls
                && targetIndex.equals(((DeleteSportCommand) other).targetIndex)); // state check
    }
}
