package seedu.address.model.game.exceptions;

/**
 * Signals that the operation will result in duplicate Groups (Groups are
 * considered duplicates if they have the same group name).
 */
public class DuplicateGameException extends RuntimeException {
    public DuplicateGameException() {
        super("Operation would result in duplicate groups");
    }
}
