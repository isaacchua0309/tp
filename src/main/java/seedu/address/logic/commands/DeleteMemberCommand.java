package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * Removes a member (participant) from an existing game.
 */
public class DeleteMemberCommand extends Command {

    public static final String COMMAND_WORD = "deletemember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a member from the game identified by the index number used in the displayed game list.\n"
            + "Parameters: g/INDEX (must be a positive integer) n/PERSON_NAME\n"
            + "Example: " + COMMAND_WORD + " g/1 n/John Doe\n";

    public static final String MESSAGE_SUCCESS = "Removed member: %1$s from game: %2$s";
    public static final String MESSAGE_PERSON_NOT_IN_GAME =
            "Member '%1$s' is not in game '%2$s'.";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "No person named '%1$s' was found in the address book.";
    public static final String MESSAGE_INVALID_GAME_INDEX = "The game index %1$d is invalid.";
    private final Index targetIndex;
    private final String memberName;

    /**
     * @param targetIndex Index of the game in the currently displayed game lis
     * @param memberName  Name of the Person to remove from that game
     */
    public DeleteMemberCommand(Index targetIndex, String memberName) {
        requireNonNull(targetIndex);
        requireNonNull(memberName);
        this.targetIndex = targetIndex;
        this.memberName = memberName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);


        List<Game> lastShownList = model.getFilteredGameList();


        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_GAME_INDEX, targetIndex.getOneBased()));
        }


        Game gameToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (memberName.trim().isEmpty()) {
            throw new CommandException("Member name cannot be empty.");
        }
        int uniqueness = model.isPersonUnique(memberName);

        if (uniqueness == 0) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, memberName));
        }

        Person personToRemove = model.getPerson(memberName);

        if (!gameToEdit.getParticipants().contains(personToRemove)) {
            throw new CommandException(String.format(
                    MESSAGE_PERSON_NOT_IN_GAME, memberName, gameToEdit.toString()));
        }

        model.deleteGame(gameToEdit);
        gameToEdit.removeParticipant(personToRemove);
        model.addGame(gameToEdit);

        return new CommandResult(String.format(MESSAGE_SUCCESS, memberName, gameToEdit.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DeleteMemberCommand
                && targetIndex.equals(((DeleteMemberCommand) other).targetIndex)
                && memberName.equals(((DeleteMemberCommand) other).memberName));
    }
}
