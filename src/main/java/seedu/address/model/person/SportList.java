package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * A specialized list implementation for managing Sports associated with a Person.
 * This class replaces direct ArrayList usage with a more encapsulated approach.
 * Provides immutability for read operations while allowing controlled modifications.
 */
public class SportList implements Iterable<Sport> {

    private final List<Sport> internalList = new ArrayList<>();

    /**
     * Creates an empty SportList.
     */
    public SportList() {
        // default empty constructor
    }

    /**
     * Creates a SportList containing the elements of the specified collection.
     *
     * @param sports collection containing elements to be added to this list
     */
    public SportList(List<Sport> sports) {
        requireNonNull(sports);
        internalList.addAll(sports);
    }

    /**
     * Returns the number of sports in this list.
     *
     * @return the number of sports in this list
     */
    public int size() {
        return internalList.size();
    }

    /**
     * Returns true if this list contains no sports.
     *
     * @return true if this list contains no sports
     */
    public boolean isEmpty() {
        return internalList.isEmpty();
    }

    /**
     * Returns true if this list contains the specified sport.
     *
     * @param sport sport whose presence in this list is to be tested
     * @return true if this list contains the specified sport
     */
    public boolean contains(Sport sport) {
        requireNonNull(sport);
        return internalList.contains(sport);
    }

    /**
     * Adds a sport to the list.
     *
     * @param sport sport to be added to this list
     */
    public void add(Sport sport) {
        requireNonNull(sport);
        internalList.add(sport);
    }

    /**
     * Adds all of the sports in the specified collection to this list.
     *
     * @param sports collection containing sports to be added to this list
     * @return true if this list changed as a result of the call
     */
    public boolean addAll(Collection<Sport> sports) {
        requireNonNull(sports);
        return internalList.addAll(sports);
    }

    /**
     * Removes the specified sport from this list, if it is present.
     *
     * @param sport sport to be removed from this list, if present
     * @return true if this list contained the specified sport
     */
    public boolean remove(Sport sport) {
        requireNonNull(sport);
        return internalList.remove(sport);
    }

    /**
     * Returns an unmodifiable view of the sports list.
     * This list will not allow modification operations such as add, remove.
     *
     * @return an unmodifiable view of the sports list
     */
    public List<Sport> asUnmodifiableList() {
        return Collections.unmodifiableList(internalList);
    }

    /**
     * Returns a new SportList containing all sports in this list.
     *
     * @return a new SportList containing all sports in this list
     */
    public SportList copy() {
        return new SportList(new ArrayList<>(internalList));
    }

    @Override
    public Iterator<Sport> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SportList otherSportList)) {
            return false;
        }

        return internalList.equals(otherSportList.internalList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(internalList);
    }

    @Override
    public String toString() {
        return internalList.stream()
                .map(Sport::toString)
                .collect(Collectors.joining(", "));
    }
}
