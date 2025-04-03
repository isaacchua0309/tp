package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.List;

import seedu.address.commons.util.LocationUtil;
import seedu.address.model.game.Game;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Game objects.
 */
public class GameBuilder {

    public static final Sport DEFAULT_SPORT = new Sport("badminton");
    public static final LocalDateTime DEFAULT_DATE_TIME =
            LocalDateTime.of(2025, 4, 4, 15, 0, 0);
    public static final String DEFAULT_LOCATION = "579543";
    public static final List<Person> DEFAULT_PARTICIPANTS = List.of(TypicalPersons.ALICE, TypicalPersons.BENSON);

    private Sport sport;
    private LocalDateTime dateTime;
    private Location location;

    private List<Person> personList;


    /**
     * Creates a {@code GameBuilder} with the default details.
     */
    public GameBuilder() {
        sport = DEFAULT_SPORT;
        dateTime = DEFAULT_DATE_TIME;
        location = LocationUtil.createLocation(DEFAULT_LOCATION);
        personList = DEFAULT_PARTICIPANTS;
    }

    /**
     * Initializes the GameBuilder with the data of {@code gameToCopy}.
     */
    public GameBuilder(Game gameToCopy) {
        sport = gameToCopy.getSport();
        dateTime = gameToCopy.getDateTime();
        location = gameToCopy.getLocation();
        personList = gameToCopy.getParticipants();
    }

    /**
     * Sets the {@code sport} of the {@code Game} that we are building.
     */
    public GameBuilder withSport(String sport) {
        this.sport = new Sport(sport);
        return this;
    }

    /**
     * Sets the {@code DateTime} of the {@code Game} that we are building.
     */
    public GameBuilder withDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code Game} that we are building.
     */
    public GameBuilder withLocation(String location) {
        this.location = LocationUtil.createLocation(location);
        return this;
    }

    /**
     * Parses the {@code Persons} into a {@code List<Person>} and set it to the {@code Game} that we are building.
     */
    public GameBuilder withPersons(Person ... persons) {
        this.personList = SampleDataUtil.getPersonList(persons);
        return this;
    }

    public Game build() {
        return new Game(sport, dateTime, location, personList);
    }
}
