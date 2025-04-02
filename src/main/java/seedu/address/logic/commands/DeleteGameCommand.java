package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;

/**
 * Deletes a game identified using its displayed index from the address book.
 * The game list is sorted by date/time, so the index corresponds to the game's position in this sorted list.
 */
public class DeleteGameCommand extends Command {
    public static final String COMMAND_WORD = "deletegame";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the game identified by the index number used in the displayed game list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 2";

    public static final String MESSAGE_DELETE_GAME_SUCCESS = "Game deleted successfully: %1$s";

    private final Index targetIndex;

    public DeleteGameCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Game> lastShownList = model.getFilteredGameList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GAME_DISPLAYED_INDEX);
        }

        Game gameToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deleteGame(gameToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_GAME_SUCCESS, Messages.format(gameToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteGameCommand)) {
            return false;
        }

        DeleteGameCommand otherDeleteGameCommand = (DeleteGameCommand) other;
        return targetIndex.equals(otherDeleteGameCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
