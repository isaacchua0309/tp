package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * Adds an existing person as a participant (“member”) to an existing game.
 */
public class AddMemberCommand extends Command {

    public static final String COMMAND_WORD = "addmember";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a member to the game identified by the index number used in the displayed game list.\n"
            + "Parameters: g/INDEX (must be a positive integer) n/PERSON_NAME\n"
            + "Example: " + COMMAND_WORD + " g/1 n/John Doe\n";

    public static final String MESSAGE_SUCCESS = "Added member: %1$s to game: %2$s";
    public static final String MESSAGE_PERSON_EXISTS =
            "Member '%1$s' is already in game '%2$s'.";
    public static final String MESSAGE_DUPLICATE_PERSONS =
            "More than one person named '%1$s' found. Please specify a unique name.";
    public static final String MESSAGE_PERSON_NOT_FOUND =
            "No person named '%1$s' is in our current address book. We recommend adding them as a person!.";
    public static final String MESSAGE_INVALID_GAME_INDEX = "The game index %1$d is invalid.";

    private final Index targetIndex;
    private final String memberName;

    /**
     * @param targetIndex Index of the game in the currently displayed game list
     * @param memberName  The name of the Person to add as a participant
     */
    public AddMemberCommand(Index targetIndex, String memberName) {
        requireNonNull(targetIndex);
        requireNonNull(memberName);
        this.targetIndex = targetIndex;
        this.memberName = memberName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // 1) Get the current filtered game list
        List<Game> lastShownList = model.getFilteredGameList();

        // 2) Validate the index
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(
                    String.format(MESSAGE_INVALID_GAME_INDEX, targetIndex.getOneBased()));
        }

        Game gameToEdit = lastShownList.get(targetIndex.getZeroBased());

        // 3) Check if multiple persons share the same name (adapt if your design differs)
        if (model.isPersonUnique(memberName) == -1) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_PERSONS, memberName));
        } else if (model.isPersonUnique(memberName) == 0) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, memberName));
        }

        // 4) Retrieve the Person to add by name
        Person personToAdd = model.getPerson(memberName);

        // 5) Check if this person is already in the participants list
        if (gameToEdit.getParticipants().contains(personToAdd)) {
            throw new CommandException(String.format(
                    MESSAGE_PERSON_EXISTS, memberName, gameToEdit.toString()));
        }

        // 6) Remove and re-add the updated game, or use a “model.addParticipantToGame(...)” approach
        model.deleteGame(gameToEdit); // remove old version
        gameToEdit.addParticipant(personToAdd); // mutate it
        model.addGame(gameToEdit); // add back the updated version

        // 7) (Optional) Re-sort or update filters if you want:
        // model.sortFilteredGameList();

        return new CommandResult(String.format(MESSAGE_SUCCESS, memberName, gameToEdit.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddMemberCommand
                && targetIndex.equals(((AddMemberCommand) other).targetIndex)
                && memberName.equals(((AddMemberCommand) other).memberName));
    }
}
