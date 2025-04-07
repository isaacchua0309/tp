package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.game.Game;
import seedu.address.model.game.UniqueGameList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level.
 * Duplicates are not allowed (by .isSamePerson comparison).
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueGameList games = new UniqueGameList();

    /*
     * Non-static initialization block to avoid duplication between constructors.
     */
    {
        persons = new UniquePersonList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Games in {@code toBeCopied}.
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }



    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the game list with {@code games}.
     * {@code games} must not contain duplicate games.
     */
    public void setGames(List<Game> games) {
        requireNonNull(games);
        this.games.setGames(games);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setPersons(newData.getPersonList());
        setGames(newData.getGameList());
    }



    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns person if person with the same identity as {@code person} exists in the address book.
     */
    public Person getPerson(String person) {
        requireNonNull(person);
        return persons.getPerson(person);
    }

    /**
     * returns true if person from the list is unique.
     * The person must already exist in the list.
     */
    public boolean isPersonUnique(String nameOfPersonToGet) {
        requireNonNull(nameOfPersonToGet);
        return persons.isPersonUnique(nameOfPersonToGet);
    }
    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);
        persons.setPerson(target, editedPerson);
    }


    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }



    /**
     * Returns an unmodifiable view of the game list.
     */
    @Override
    public ObservableList<Game> getGameList() {
        return games.asUnmodifiableObservableList();
    }

    /**
     * Validates that all participants in a game exist in the address book.
     * @throws IllegalValueException if any participant does not exist.
     */
    public void validateGameParticipants(Game game) throws IllegalValueException {
        for (Person participant : game.getParticipants()) {
            if (!hasPerson(participant)) {
                throw new IllegalValueException(
                    "Game contains participant that does not exist in address book: "
                    + participant.getName().fullName);
            }
        }
    }

    /**
     * Adds a game to the address book.
     * The game must not already exist in the address book.
     */
    public void addGame(Game game) {
        requireNonNull(game);
        games.add(game);
    }

    /**
     * Adds a game to the address book with participant validation.
     * The game must not already exist in the address book.
     * @throws IllegalArgumentException if any participant does not exist in the address book.
     */
    public void addGameWithValidation(Game game) {
        requireNonNull(game);
        try {
            validateGameParticipants(game);
            games.add(game);
        } catch (IllegalValueException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    /**
     * Returns true if a game with the same identity exists in the address book.
     *
     * @param game the game to check.
     * @return true if the game exists.
     */
    public boolean hasGame(Game game) {
        requireNonNull(game);
        return games.contains(game);
    }

    /**
     * Replaces the given game {@code target} in the list with {@code editedGame}.
     * {@code target} must exist in the address book.
     * The game identity of {@code editedGame} must not be the same as another existing game in the address book.
     */
    public void setGame(Game target, Game editedGame) {
        requireNonNull(editedGame);
        games.setGame(target, editedGame);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeGame(Game key) {
        games.remove(key);
    }



    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("games", games)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof AddressBook)) {
            return false;
        }
        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && games.equals(otherAddressBook.games);
    }

    @Override
    public int hashCode() {
        return persons.hashCode() + games.hashCode();
    }
}
