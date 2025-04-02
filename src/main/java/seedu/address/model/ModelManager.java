package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.game.Game;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;

    // A filtered list of persons, which we further wrap in a SortedList
    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;

    // A filtered list of games, further wrapped in a SortedList for consistent date-based ordering
    private final FilteredList<Game> filteredGames;
    private final SortedList<Game> sortedGames;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);

        // Persons
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);

        // Games - initialize with date/time sorting to ensure consistent indexing
        filteredGames = new FilteredList<>(this.addressBook.getGameList());
        sortedGames = new SortedList<>(filteredGames, Comparator.comparing(Game::getDateTime));
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    // ========== UserPrefs Methods ============================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    // ========== AddressBook Methods ==========================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    // ========== Person-level Operations ======================================================

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(target);

        // Get all games
        List<Game> allGames = new ArrayList<>(addressBook.getGameList());

        // Check each game if the person is a participant
        for (Game game : allGames) {
            if (game.getParticipants().contains(target)) {
                // Remove the person from the game participants
                Game updatedGame = new Game(
                    game.getSport(),
                    game.getDateTime(),
                    game.getLocation(),
                    game.getParticipants().stream()
                        .filter(p -> !p.equals(target))
                        .collect(Collectors.toList())
                );

                // Replace the old game with the updated one
                addressBook.removeGame(game);
                addressBook.addGame(updatedGame);
            }
        }

        // Finally, remove the person from the address book
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        // Refresh the filtered list so the added person is visible
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }

    // ========== Game-level Operations ========================================================

    @Override
    public boolean hasGame(Game game) {
        requireNonNull(game);
        return addressBook.hasGame(game);
    }

    @Override
    public void deleteGame(Game target) {
        addressBook.removeGame(target);
    }

    @Override
    public void addGame(Game game) {
        requireNonNull(game);
        addressBook.addGame(game);
    }

    @Override
    public ObservableList<Game> getGameList() {
        // Returns the internal list of all games
        return addressBook.getGameList();
    }

    // ========== Filtered Person and Game List Accessors ======================================

    @Override
    public ObservableList<Person> getFilteredPersonList() {
        // We return the sorted version so the UI or logic always sees the sorted list
        return sortedPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void sortFilteredPersonListByDistance(Location location) {
        requireNonNull(location);
        // We define a custom comparator that sorts Person objects by distance
        Comparator<Person> comparator =
                Comparator.comparingDouble(person -> person.getLocation().distanceTo(location));
        sortedPersons.setComparator(comparator);
    }

    @Override
    public ObservableList<Game> getFilteredGameList() {
        // Return the sorted games list so UI and commands always see consistently ordered games
        return sortedGames;
    }

    @Override
    public void updateFilteredGameList(Predicate<Game> predicate) {
        requireNonNull(predicate);
        filteredGames.setPredicate(predicate);
    }

    // ========== Equality Check ===============================================================

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredPersons.equals(otherModelManager.filteredPersons);
    }

    @Override
    public int isPersonUnique(String name) {
        requireNonNull(name);
        long count = addressBook.getPersonList().stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                .count();
        // "Unique" if there is exactly 1 match
        if (count == 1) {
            return 1;
        } else if (count == 0) {
            return 0;
        } else {
            return -1; // More than one match
        }
    }

    @Override
    public Person getPerson(String name) {
        requireNonNull(name);
        // Find all persons matching this name (case-insensitive).
        List<Person> matchedPersons = addressBook.getPersonList().stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (matchedPersons.size() > 1) {
            // If multiple matches, you could return an arbitrary one or throw an exception.
            // Usually you'd throw something like a CommandException, or a custom exception.
            throw new IllegalArgumentException(
                    "Multiple persons found with the name: " + name + ". Please be more specific.");
        } else if (matchedPersons.isEmpty()) {
            throw new IllegalArgumentException("No person found with the name: " + name);
        }

        // Exactly one match
        return matchedPersons.get(0);
    }

}
