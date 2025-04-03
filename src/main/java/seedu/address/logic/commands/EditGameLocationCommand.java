package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
            + "Parameters: g/INDEX pc/NEW_POSTAL_CODE\n"
            + "Example: " + COMMAND_WORD + " g/1 pc/018906";

    public static final String MESSAGE_SUCCESS = "Game location changed successfully: %1$s";
    public static final String MESSAGE_GAME_NOT_FOUND = "The game with the given index does not exist.";
    public static final String MESSAGE_INVALID_POSTAL_CODE = "Invalid. Please enter a valid Singapore postal code.";

    private final int gameIndex;
    private final String newPostalCode;

    /**
     * Creates an EditGameLocationCommand to change the location of the specified game.
     */
    public EditGameLocationCommand(int gameIndex, String newPostalCode) {
        requireNonNull(newPostalCode);
        this.gameIndex = gameIndex;
        this.newPostalCode = newPostalCode;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // Check if the game exists in the model (by index)
        if (gameIndex < 0 || gameIndex >= model.getGameList().size()) {
            throw new CommandException(MESSAGE_GAME_NOT_FOUND);
        }

        Game gameToChange = model.getGameList().get(gameIndex);

        // Check if the postal code is valid
        String newLocation;
        try {
            newLocation = ParserUtil.parsePostalCode(newPostalCode);
        } catch (ParseException e) {
            throw new CommandException(MESSAGE_INVALID_POSTAL_CODE);
        }

        // Change the location of the game
        gameToChange.setLocation(LocationUtil.createLocation(newLocation));

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
