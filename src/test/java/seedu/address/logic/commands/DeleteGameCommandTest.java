package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_OBJECT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OBJECT;
import static seedu.address.testutil.TypicalGames.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.game.Game;

public class DeleteGameCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Game gameToDelete = model.getFilteredGameList().get(INDEX_FIRST_OBJECT.getZeroBased());
        DeleteGameCommand deleteGameCommand = new DeleteGameCommand(INDEX_FIRST_OBJECT);

        String expectedMessage = String.format(DeleteGameCommand.MESSAGE_DELETE_GAME_SUCCESS,
                Messages.format(gameToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deleteGame(gameToDelete);

        assertCommandSuccess(deleteGameCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredGameList().size() + 1);
        DeleteGameCommand deleteGameCommand = new DeleteGameCommand(outOfBoundIndex);

        assertCommandFailure(deleteGameCommand, model, Messages.MESSAGE_INVALID_GAME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteGameCommand deleteFirstCommand = new DeleteGameCommand(INDEX_FIRST_OBJECT);
        DeleteGameCommand deleteSecondCommand = new DeleteGameCommand(INDEX_SECOND_OBJECT);


        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));


        DeleteGameCommand deleteFirstCommandCopy = new DeleteGameCommand(INDEX_FIRST_OBJECT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));


        assertFalse(deleteFirstCommand.equals(1));


        assertFalse(deleteFirstCommand.equals(null));


        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteGameCommand deleteGameCommand = new DeleteGameCommand(targetIndex);
        String expected = DeleteGameCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteGameCommand.toString());
    }
}
