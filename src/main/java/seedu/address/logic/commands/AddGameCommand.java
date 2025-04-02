package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;

/**
 * Adds a game in the address book.
 */
public class AddGameCommand extends Command {

    public static final String COMMAND_WORD = "addgame";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a new game to the address book.\n"
            + "Parameters: g/GAME_NAME dt/DATE_TIME pc/LOCATION\n"
            + "Example: " + COMMAND_WORD + " g/Soccer dt/2021-09-20T15:00:00 pc/018906";

    public static final String MESSAGE_SUCCESS = "New game scheduled successfully: %1$s";
    public static final String MESSAGE_DUPLICATE_GAME = "A game is already scheduled at this time. \n"
            + "Please choose a different time for your game.";

    private final Game game;

    /**
     * Creates an AddGameCommand to add the specified {@code Game}.
     */
    public AddGameCommand(Game game) {
        requireNonNull(game);
        this.game = game;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasGame(game)) {
            throw new CommandException(MESSAGE_DUPLICATE_GAME);
        }

        model.addGame(game);
        return new CommandResult(String.format(MESSAGE_SUCCESS, game));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return other instanceof AddGameCommand
                && ((AddGameCommand) other).game.equals(this.game);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("game", game)
                .toString();
    }
}
