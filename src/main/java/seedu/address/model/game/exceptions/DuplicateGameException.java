package seedu.address.model.game.exceptions;

/**
 * Signals that the operation will result in duplicate Games (Games are
 * considered duplicates if they have the same date/time).
 */
public class DuplicateGameException extends RuntimeException {
    /**
     * Constructs a DuplicateGameException with a default error message.
     * The message indicates that a scheduling conflict exists because
     * a game is already scheduled at that time.
     */
    public DuplicateGameException() {
        super("Operation would result in a scheduling conflict. A game is already scheduled at this time. "
                + "Please choose a different time.");
    }
}
