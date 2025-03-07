package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

/**
 * Represents a Remark for a Person in the address book.
 * Guarantees: the remark is present and not null.
 */
public class Remark {
    private final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param value A valid remark string.
     */
    public Remark(String value) {
        requireNonNull(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value));
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
