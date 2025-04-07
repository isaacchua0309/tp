package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGames.SOCCER;
import static seedu.address.testutil.TypicalGames.VOLLEYBALL;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.game.exceptions.DuplicateGameException;
import seedu.address.model.game.exceptions.GameNotFoundException;
import seedu.address.testutil.GameBuilder;

public class UniqueGameListTest {
    private static final LocalDateTime VALID_DATETIME = LocalDateTime.of(
            2025, 5, 3, 12, 12, 0);
    private final UniqueGameList uniqueGameList = new UniqueGameList();
    @Test
    public void contains_nullGame_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.contains(null));
    }

    @Test
    public void contains_gameNotInList_returnsFalse() {
        assertFalse(uniqueGameList.contains(SOCCER));
    }

    @Test
    public void contains_gameInList_returnsTrue() {
        uniqueGameList.add(SOCCER);
        assertTrue(uniqueGameList.contains(SOCCER));
    }

    @Test
    public void contains_gameWithSameIdentityFieldsInList_returnsTrue() {
        uniqueGameList.add(VOLLEYBALL);
        Game editedGame = new GameBuilder(VOLLEYBALL).withLocation("402001").build();
        assertTrue(uniqueGameList.contains(editedGame));
    }

    @Test
    public void add_nullGame_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.add(null));
    }

    @Test
    public void add_duplicateGame_throwsDuplicateGameException() {
        uniqueGameList.add(VOLLEYBALL);
        assertThrows(DuplicateGameException.class, () -> uniqueGameList.add(VOLLEYBALL));
    }

    @Test
    public void setGame_nullTargetGame_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.setGame(null, SOCCER));
    }

    @Test
    public void setGame_nullEditedGame_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.setGame(SOCCER, null));
    }

    @Test
    public void setGame_targetGameNotInList_throwsGameNotFoundException() {
        assertThrows(GameNotFoundException.class, () -> uniqueGameList.setGame(SOCCER, SOCCER));
    }

    @Test
    public void setGame_editedGameIsSameGame_success() {
        uniqueGameList.add(VOLLEYBALL);
        uniqueGameList.setGame(VOLLEYBALL, VOLLEYBALL);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        expectedUniqueGameList.add(VOLLEYBALL);
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGame_editedGameHasSameIdentity_success() {
        uniqueGameList.add(VOLLEYBALL);
        Game editedGame = new GameBuilder(VOLLEYBALL).withDateTime(VALID_DATETIME).build();
        uniqueGameList.setGame(VOLLEYBALL, editedGame);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        expectedUniqueGameList.add(editedGame);
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGame_editedGameHasDifferentIdentity_success() {
        uniqueGameList.add(SOCCER);
        uniqueGameList.setGame(SOCCER, VOLLEYBALL);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        expectedUniqueGameList.add(VOLLEYBALL);
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGame_editedGameHasNonUniqueIdentity_throwsDuplicateGameException() {
        uniqueGameList.add(SOCCER);
        uniqueGameList.add(VOLLEYBALL);
        assertThrows(DuplicateGameException.class, () -> uniqueGameList.setGame(SOCCER, VOLLEYBALL));
    }

    @Test
    public void remove_nullGame_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.remove(null));
    }

    @Test
    public void remove_gameDoesNotExist_throwsgameNotFoundException() {
        assertThrows(GameNotFoundException.class, () -> uniqueGameList.remove(VOLLEYBALL));
    }

    @Test
    public void remove_existingGame_removesGame() {
        uniqueGameList.add(VOLLEYBALL);
        uniqueGameList.remove(VOLLEYBALL);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGames_nullUniqueGameList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.setGames((UniqueGameList) null));
    }

    @Test
    public void setGames_uniqueGameList_replacesOwnListWithProvidedUniqueGameList() {
        uniqueGameList.add(VOLLEYBALL);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        expectedUniqueGameList.add(SOCCER);
        uniqueGameList.setGames(expectedUniqueGameList);
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGames_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueGameList.setGames((List<Game>) null));
    }

    @Test
    public void setGames_list_replacesOwnListWithProvidedList() {
        uniqueGameList.add(VOLLEYBALL);

        List<Game> gameList = Collections.singletonList(VOLLEYBALL);
        uniqueGameList.setGames(gameList);
        UniqueGameList expectedUniqueGameList = new UniqueGameList();
        expectedUniqueGameList.add(VOLLEYBALL);
        assertEquals(expectedUniqueGameList, uniqueGameList);
    }

    @Test
    public void setGames_listWithDuplicateGames_throwsDuplicateGameException() {
        List<Game> listWithDuplicateGames = Arrays.asList(SOCCER, SOCCER);
        assertThrows(DuplicateGameException.class, () -> uniqueGameList.setGames(listWithDuplicateGames));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
                -> uniqueGameList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void toStringMethod() {
        assertEquals(uniqueGameList.asUnmodifiableObservableList().toString(), uniqueGameList.toString());
    }
}
