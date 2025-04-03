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


    private final FilteredList<Person> filteredPersons;
    private final SortedList<Person> sortedPersons;


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


        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);


        filteredGames = new FilteredList<>(this.addressBook.getGameList());
        sortedGames = new SortedList<>(filteredGames, Comparator.comparing(Game::getDateTime));
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }



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



    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }



    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        requireNonNull(target);


        List<Game> allGames = new ArrayList<>(addressBook.getGameList());


        for (Game game : allGames) {
            if (game.getParticipants().contains(target)) {

                Game updatedGame = new Game(
                    game.getSport(),
                    game.getDateTime(),
                    game.getLocation(),
                    game.getParticipants().stream()
                        .filter(p -> !p.equals(target))
                        .collect(Collectors.toList())
                );


                addressBook.removeGame(game);
                addressBook.addGame(updatedGame);
            }
        }


        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);
        addressBook.setPerson(target, editedPerson);
    }



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

        return addressBook.getGameList();
    }



    @Override
    public ObservableList<Person> getFilteredPersonList() {

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

        Comparator<Person> comparator =
                Comparator.comparingDouble(person -> person.getLocation().distanceTo(location));
        sortedPersons.setComparator(comparator);
    }


    /**
     * Sorts the filtered person list alphabetically.
     */
    @Override
    public void sortFilteredPersonListAlphabetically() {
        Comparator<Person> comparator = Comparator.comparing(person -> person.getName().fullName);
        sortedPersons.setComparator(comparator);
    }


    /**
     * Sorts the filtered group list alphabetically.
     */
    @Override
    public ObservableList<Game> getFilteredGameList() {

        return sortedGames;
    }

    @Override
    public void updateFilteredGameList(Predicate<Game> predicate) {
        requireNonNull(predicate);
        filteredGames.setPredicate(predicate);
    }



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

        if (count == 1) {
            return 1;
        } else if (count == 0) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public Person getPerson(String name) {
        requireNonNull(name);

        List<Person> matchedPersons = addressBook.getPersonList().stream()
                .filter(p -> p.getName().fullName.equalsIgnoreCase(name))
                .collect(Collectors.toList());

        if (matchedPersons.size() > 1) {


            throw new IllegalArgumentException(
                    "Multiple persons found with the name: " + name + ". Please be more specific.");
        } else if (matchedPersons.isEmpty()) {
            throw new IllegalArgumentException("No person found with the name: " + name);
        }


        return matchedPersons.get(0);
    }

}
