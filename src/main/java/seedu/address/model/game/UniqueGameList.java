package seedu.address.model.game;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.game.exceptions.DuplicateGameException;
import seedu.address.model.game.exceptions.GameNotFoundException;

/**
 * A list of games that enforces uniqueness between its elements and does not allow nulls.
 * A game is considered unique based on its sport and scheduled date/time.
 * <p>
 * Supports a minimal set of list operations, and automatically sorts the games by date/time.
 */
public class UniqueGameList implements Iterable<Game> {
    private final ObservableList<Game> internalList = FXCollections.observableArrayList();
    private final ObservableList<Game> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Adds a game to the list.
     * The game must not already exist in the list.
     *
     * @param game The game to be added.
     * @throws DuplicateGameException if the game already exists.
     */
    public void add(Game game) {
        requireNonNull(game);
        if (contains(game)) {
            throw new DuplicateGameException();
        }
        internalList.add(game);
        sortByDate();
    }

    /**
     * Removes the equivalent game from the list.
     * The game must exist in the list.
     *
     * @param toRemove the game to remove.
     * @throws GameNotFoundException if the game is not found.
     */
    public void remove(Game toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new GameNotFoundException();
        }
    }

    /**
     * Returns true if a game with the same identity exists in the list.
     *
     * @param gameToCheck the game to check for.
     * @return true if the game exists.
     */
    public boolean contains(Game gameToCheck) {
        requireNonNull(gameToCheck);
        return internalList.stream().anyMatch(gameToCheck::isSameGame);
    }

    /**
     * Replaces the game {@code target} in the list with {@code editedGame}.
     * {@code target} must exist in the list.
     * The game identity of {@code editedGame} must not be the same as another existing game in the list.
     *
     * @param target     the game to be replaced.
     * @param editedGame the new game.
     * @throws GameNotFoundException  if {@code target} is not found.
     * @throws DuplicateGameException if the replacement would cause a duplicate.
     */
    public void setGame(Game target, Game editedGame) {
        requireAllNonNull(target, editedGame);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new GameNotFoundException();
        }

        if (!target.isSameGame(editedGame) && contains(editedGame)) {
            throw new DuplicateGameException();
        }

        internalList.set(index, editedGame);
        sortByDate();
    }

    /**
     * Replaces the contents of this list with {@code games}.
     * {@code games} must not contain duplicate games.
     *
     * @param games the list of games to set.
     * @throws DuplicateGameException if duplicates exist in {@code games}.
     */
    public void setGames(List<Game> games) {
        requireAllNonNull(games);
        if (!gamesAreUnique(games)) {
            throw new DuplicateGameException();
        }
        internalList.setAll(games);
        sortByDate();
    }

    /**
     * Replaces the contents of this list with {@code replacement}.
     * {@code replacement} must not contain duplicate games.
     *
     * @param replacement another UniqueGameList.
     */
    public void setGames(UniqueGameList replacement) {
        requireNonNull(replacement);
        setGames(replacement.internalList);
    }

    /**
     * Returns an unmodifiable observable list of games.
     *
     * @return the unmodifiable list.
     */
    public ObservableList<Game> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns true if {@code games} contains only unique games
     * (i.e., no two games have the same sport and scheduled date/time).
     */
    private boolean gamesAreUnique(List<Game> games) {
        for (int i = 0; i < games.size() - 1; i++) {
            for (int j = i + 1; j < games.size(); j++) {
                if (games.get(i).isSameGame(games.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Sorts the internal list by game date/time in ascending order.
     */
    private void sortByDate() {
        internalList.sort(Comparator.comparing(Game::getDateTime));
    }

    @Override
    public Iterator<Game> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof UniqueGameList)) {
            return false;
        }
        UniqueGameList otherList = (UniqueGameList) other;
        return internalList.equals(otherList.internalList);
    }
}
