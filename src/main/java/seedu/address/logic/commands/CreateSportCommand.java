package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
    private final Path filePath = Paths.get("data" , "globalSportList.json");

    /**
     * Creates a CreateSportCommand to create the specified sport.
     *
     * @param sportName The name of the sport to be created.
     */
    public CreateSportCommand(String sportName) {
        requireNonNull(sportName);
        this.sportName = sportName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        // Check if the sport already exists
        if (!Sport.createValidSport(sportName)) {
            throw new CommandException(MESSAGE_DUPLICATE_SPORT);
        }

        try {
            Sport.saveValidSports(filePath);
        } catch (IOException e) {
            throw new CommandException("Error saving sports to file");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, sportName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CreateSportCommand // instanceof handles nulls
                && sportName.equals(((CreateSportCommand) other).sportName));
    }
}
