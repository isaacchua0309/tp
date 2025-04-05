package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Sport;

/**
 * Creates a new sport in the global sport list.
 */
public class CreateSportCommand extends Command {
    public static final String COMMAND_WORD = "createsport";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates a new sport. "
            + "Parameters: s/SPORT_NAME\n"
            + "Example: " + COMMAND_WORD + " s/Boxing";
    public static final String MESSAGE_SUCCESS = "New sport created: %1$s";
    public static final String MESSAGE_DUPLICATE_SPORT = "This sport already exists";

    private final String sportName;
    private final Path filePath;

    /**
     * Default constructor that uses the file path from UserPrefs.
     * The actual file path will be retrieved from the model during execution.
     *
     * @param sportName The name of the sport to be created.
     */
    public CreateSportCommand(String sportName) {
        requireNonNull(sportName);
        this.sportName = sportName;
        this.filePath = null; // Will use the path from UserPrefs during execution
    }

    /**
     * Constructor with configurable file path for testing.
     *
     * @param sportName The name of the sport to be created.
     * @param filePath The custom file path to save sports to.
     */
    public CreateSportCommand(String sportName, Path filePath) {
        requireNonNull(sportName);
        requireNonNull(filePath);
        this.sportName = sportName;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Check if the sport already exists
        if (!Sport.createValidSport(sportName)) {
            throw new CommandException(MESSAGE_DUPLICATE_SPORT);
        }

        try {
            // Use the provided file path or get it from UserPrefs if not provided
            Path pathToUse = filePath != null ? filePath : model.getUserPrefs().getGlobalSportsListFilePath();
            Sport.saveValidSports(pathToUse);
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_SUCCESS, sportName));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sportName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same objec
                || (other instanceof CreateSportCommand // instanceof handles nulls
                && sportName.equals(((CreateSportCommand) other).sportName));
    }
}
