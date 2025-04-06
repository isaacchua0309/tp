package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.util.LocationUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;

/**
 * Changes the location of an existing game.
 */
public class EditGameLocationCommand extends Command {

    public static final String COMMAND_WORD = "editgamelocation";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the location of an existing game.\n"
            + "Parameters: g/INDEX(must be more than 0) pc/NEW_POSTAL_CODE\n"
            + "Example: " + COMMAND_WORD + " g/1 pc/018906";

    public static final String MESSAGE_SUCCESS = "Game location changed successfully.";
    public static final String MESSAGE_GAME_NOT_FOUND = "The game with the given index does not exist.";
    public static final String MESSAGE_INVALID_POSTAL_CODE = "Invalid. Please enter a valid Singapore postal code.";

    private static final Logger logger = Logger.getLogger(EditGameLocationCommand.class.getName());

    private final int gameIndex;
    private final String newPostalCode;

    /**
     * Constructs an EditGameLocationCommand with the specified game index and new postal code.
     *
     * @param gameIndex Index of the game to be edited.
     * @param newPostalCode New postal code representing the game's location.
     */
    public EditGameLocationCommand(int gameIndex, String newPostalCode) {
        requireNonNull(newPostalCode); // Defensive coding: null check for newPostalCode
        assert gameIndex >= 0 : "Game index must be non-negative"; // Assertion to validate gameIndex

        this.gameIndex = gameIndex;
        this.newPostalCode = newPostalCode;
    }

    /**
     * Executes the EditGameLocationCommand by updating the location of the specified game.
     *
     * @param model Model containing the game's data.
     * @return CommandResult containing the outcome message.
     * @throws CommandException if the game index is invalid or the postal code is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model); // Defensive coding: null check for model

        if (gameIndex < 0 || gameIndex >= model.getGameList().size()) {
            // Logging: invalid game index detected
            logger.log(Level.WARNING, "Invalid game index provided: {0}", gameIndex);
            throw new CommandException(MESSAGE_GAME_NOT_FOUND);
        }

        Game gameToChange = model.getGameList().get(gameIndex);

        String newLocation;
        try {
            newLocation = ParserUtil.parsePostalCode(newPostalCode);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INVALID_POSTAL_CODE);
        }

        if (newLocation == null || newLocation.trim().isEmpty()) {
            // Logging and defensive coding: additional check for empty parsed location
            logger.log(Level.SEVERE, "Parsed location is invalid or empty for postal code: {0}", newPostalCode);
            throw new CommandException("Parsed location is invalid or empty.");
        }

        model.deleteGame(gameToChange);
        gameToChange.setLocation(LocationUtil.createLocation(newLocation));
        model.addGame(gameToChange);

        return new CommandResult(String.format(MESSAGE_SUCCESS, gameToChange));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof EditGameLocationCommand
                && ((EditGameLocationCommand) other).gameIndex == this.gameIndex
                && ((EditGameLocationCommand) other).newPostalCode.equals(this.newPostalCode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("gameIndex", gameIndex)
                .add("newPostalCode", newPostalCode)
                .toString();
    }
}
