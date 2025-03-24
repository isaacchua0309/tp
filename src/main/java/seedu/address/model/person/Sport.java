package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Sport associated with a Person in the address book.
 * Guarantees: sport name is present, not null, and is valid.
 */
public class Sport {

    public static final Set<String> VALID_SPORTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "soccer", "basketball", "tennis", "badminton", "cricket",
            "baseball", "volleyball", "hockey", "rugby", "golf"
    )));

    public static final String MESSAGE_CONSTRAINTS = "Sport must be one of the following: "
            + String.join(", ", VALID_SPORTS);

    public final String sportName;

    /**
     * Constructs a {@code Sport}.
     *
     * @param sportName A valid sport name.
     */
    public Sport(String sportName) {
        requireNonNull(sportName);
        this.sportName = sportName;
    }

    /**
     * Returns true if the given sport name is valid.
     */
    public static boolean isValidSport(String sportName) {
        requireNonNull(sportName);
        return VALID_SPORTS.contains(sportName.toLowerCase().trim());
    }

    /**
     * Returns the normalized form of the sport name (lowercase).
     */
    public String getNormalizedName() {
        return sportName.toLowerCase();
    }

    @Override
    public String toString() {
        return sportName;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Sport otherSport)) {
            return false;
        }
        return sportName.equalsIgnoreCase(otherSport.sportName);
    }
    /**
     * Returns a hash code for this Sport. The hash code is computed using the sport name
     * converted to lowercase, ensuring that two Sport objects that are equal
     * (ignoring case) produce the same hash code.
     *
     * @return the hash code value for this Sport.
     */
    @Override
    public int hashCode() {
        return sportName.toLowerCase().hashCode();
    }
}
