package seedu.address.model.game;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a scheduled game event with a specific sport, date/time, location,
 * and a list of unique participants.
 */
public class Game {
    private final Sport sport;
    private final LocalDateTime dateTime;
    private Location location;
    private final UniquePersonList participants;

    /**
     * Constructs a {@code Game} with the given sport, date/time, and location,
     * and an empty list of participants.
     */
    public Game(Sport sport, LocalDateTime dateTime, Location location) {
        Objects.requireNonNull(sport);
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(location);
        this.sport = sport;
        this.dateTime = dateTime;
        this.location = location;
        this.participants = new UniquePersonList();
    }

    /**
     * Constructs a {@code Game} with the given sport, date/time, location, and
     * a list of participants.
     */
    public Game(Sport sport, LocalDateTime dateTime, Location location, List<Person> participants) {
        Objects.requireNonNull(sport);
        Objects.requireNonNull(dateTime);
        Objects.requireNonNull(location);
        Objects.requireNonNull(participants);
        this.sport = sport;
        this.dateTime = dateTime;
        this.location = location;
        UniquePersonList list = new UniquePersonList();
        list.setPersons(participants);
        this.participants = list;
    }

    public Sport getSport() {
        return sport;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Location getLocation() {
        return location;
    }

    /**
     * Returns an unmodifiable list of participants.
     */
    public List<Person> getParticipants() {
        return participants.asUnmodifiableObservableList();
    }

    public void addParticipant(Person person) {
        participants.add(person);
    }

    public void removeParticipant(Person person) {
        participants.remove(person);
    }

    /**
     * Returns true if both games have the same date/time, regardless of sport or location.
     * This prevents scheduling multiple games at the same time and ensures consisten
     * indexing based on date/time in commands and UI.
     */
    public boolean isSameGame(Game otherGame) {
        if (otherGame == this) {
            return true;
        }
        return otherGame != null
                && otherGame.getDateTime().equals(this.dateTime);
    }

    /**
     * Sets a new location for this game.
     */
    public void setLocation(Location location) {
        Objects.requireNonNull(location, "Location cannot be null");
        this.location = location;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Game)) {
            return false;
        }
        Game game = (Game) other;
        return sport.equals(game.sport)
                && dateTime.equals(game.dateTime)
                && location.equals(game.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sport, dateTime, location);
    }

    @Override
    public String toString() {
        return "Game: " + sport + " at " + dateTime + " in " + location
            + "Participants: " + participants.asUnmodifiableObservableList().size();
    }
}
