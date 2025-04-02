/**
 * A list of games that enforces uniqueness between its elements and does not allow nulls.
 * A game is considered unique based on its scheduled date/time only.
 * <p>
 * Supports a minimal set of list operations, and automatically sorts the games by date/time.
 */
public class UniqueGameList implements Iterable<Game> {
    // ... existing code ...

    /**
     * Returns true if {@code games} contains only unique games
     * (i.e., no two games have the same scheduled date/time).
     */
    private boolean gamesAreUnique(List<Game> games) {
        // ... existing code ...
    }
    
    // ... other methods ...
}
