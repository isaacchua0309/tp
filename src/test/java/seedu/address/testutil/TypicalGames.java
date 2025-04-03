package seedu.address.testutil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.game.Game;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Game} objects to be used in tests.
 */

public class TypicalGames {

    public static final LocalDateTime DATE_TIME_1 =
            LocalDateTime.of(2025, 4, 4, 15, 0, 0);
    public static final LocalDateTime DATE_TIME_2 =
            LocalDateTime.of(2025, 5, 3, 12, 12, 0);
    public static final LocalDateTime DATE_TIME_3 =
            LocalDateTime.of(2025, 12, 4, 00, 11, 23);
    public static final List<Person> LIST_OF_TYPICAL_PERSONS = TypicalPersons.getTypicalPersons();

    public static final Game VOLLEYBALL = new GameBuilder().withSport("volleyball").withDateTime(DATE_TIME_1)
            .withLocation("259366").withPersons(LIST_OF_TYPICAL_PERSONS.get(0), LIST_OF_TYPICAL_PERSONS.get(1)).build();
    public static final Game SOCCER = new GameBuilder().withSport("soccer").withDateTime(DATE_TIME_2)
            .withLocation("397629").withPersons(LIST_OF_TYPICAL_PERSONS.get(4), LIST_OF_TYPICAL_PERSONS.get(6)).build();
    public static final Game TENNIS = new GameBuilder().withSport("tennis").withDateTime(DATE_TIME_3)
            .withLocation("588406").withPersons(LIST_OF_TYPICAL_PERSONS.get(3), LIST_OF_TYPICAL_PERSONS.get(5)).build();
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Game game : getTypicalGames()) {
            ab.addGame(game);
        }
        return ab;
    }

    public static List<Game> getTypicalGames() {
        return new ArrayList<>(Arrays.asList(VOLLEYBALL, SOCCER, TENNIS));
    }

}
