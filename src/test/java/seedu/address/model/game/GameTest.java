package seedu.address.model.game;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGames.DATE_TIME_1;
import static seedu.address.testutil.TypicalGames.DATE_TIME_2;
import static seedu.address.testutil.TypicalGames.SOCCER;
import static seedu.address.testutil.TypicalGames.TENNIS;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.LocationUtil;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;
import seedu.address.testutil.GameBuilder;
import seedu.address.testutil.TypicalGames;
import seedu.address.testutil.TypicalPersons;

public class GameTest {
    public static final LocalDateTime VALID_DATETIME =
            LocalDateTime.of(2025, 4, 4, 15, 0, 0);
    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Game game = new GameBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> game.getParticipants().remove(0));
    }

    @Test
    public void isSameGame() {

        assertTrue(TENNIS.isSameGame(TENNIS));

        assertFalse(TENNIS.isSameGame(null));

        Game editedTennis = new GameBuilder(TENNIS).withLocation("402001").build();
        assertTrue(TENNIS.isSameGame(editedTennis));


        editedTennis = new GameBuilder(TENNIS).withDateTime(VALID_DATETIME).build();
        assertFalse(TENNIS.isSameGame(editedTennis));


        Game editedSoccer = new GameBuilder(SOCCER).withSport(SOCCER.getSport().sportName.toUpperCase()).build();
        assertTrue(SOCCER.isSameGame(editedSoccer));


        String nameWithTrailingSpaces = SOCCER.getSport().sportName + "3 ";
        editedSoccer = new GameBuilder(SOCCER).withSport(nameWithTrailingSpaces).build();
        assertTrue(SOCCER.isSameGame(editedSoccer)); // based on definition
    }

    @Test
    public void equals() {

        Game soccerCopy = new GameBuilder(SOCCER).build();
        assertTrue(SOCCER.equals(soccerCopy));


        assertTrue(SOCCER.equals(SOCCER));


        assertFalse(SOCCER.equals(null));


        assertFalse(SOCCER.equals(5));


        assertFalse(SOCCER.equals(TENNIS));


        Game editedTennis = new GameBuilder(TENNIS).withSport(SOCCER.getSport().sportName).build();
        assertFalse(TENNIS.equals(editedTennis));

        editedTennis = new GameBuilder(TENNIS).withLocation("402001").build();
        assertFalse(TENNIS.equals(editedTennis));


        editedTennis = new GameBuilder(TENNIS).withDateTime(VALID_DATETIME).build();
        assertFalse(TENNIS.equals(editedTennis));
    }

    @Test
    public void toStringMethod() {
        Game soccer = new GameBuilder(SOCCER).build();
        String expected = "Game: " + soccer.getSport() + " at " + soccer.getDateTime()
                + " in " + soccer.getLocation()
                + "Participants: " + soccer.getParticipants().size();
        assertEquals(expected, soccer.toString());
    }

    @Test
    public void getParticipants_modifyList_throwsUnsupportedOperationException() {
        Game game = new GameBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> game.getParticipants().remove(0));
    }

    @Test
    public void hasParticipants_gameWithNoParticipants_returnsFalse() {
        Game game = new GameBuilder().withPersons().build();
        Person person = TypicalPersons.AMY;
        assertFalse(game.getParticipants().contains(person));
    }

    @Test
    public void hasParticipant_gameWithParticipant_returnsTrue() {
        Game game = new GameBuilder().build();
        assertTrue(game.getParticipants().contains(TypicalPersons.ALICE));
    }

    @Test
    public void addParticipant_success() {
        Game originalgame = new GameBuilder().build();
        List<Person> updatedParticipants = new ArrayList<>(originalgame.getParticipants());
        updatedParticipants.add(TypicalPersons.AMY);

        Game updatedGame = new Game(
                originalgame.getSport(),
                originalgame.getDateTime(),
                originalgame.getLocation(),
                updatedParticipants);

        assertTrue(updatedGame.getParticipants().contains(TypicalPersons.AMY));
        assertEquals(originalgame.getParticipants().size() + 1, updatedGame.getParticipants().size());
    }

    @Test
    public void deleteParticipant_success() {
        Game originalgame = new GameBuilder().build();
        List<Person> updatedParticipants = new ArrayList<>(originalgame.getParticipants());
        updatedParticipants.remove(TypicalPersons.ALICE);

        Game updatedGame = new Game(
                originalgame.getSport(),
                originalgame.getDateTime(),
                originalgame.getLocation(),
                updatedParticipants);

        assertFalse(updatedGame.getParticipants().contains(TypicalPersons.ALICE));
        assertEquals(originalgame.getParticipants().size() - 1, updatedGame.getParticipants().size());
    }

    @Test
    public void getParticipantList_modifyOriginalParticipantList_doesNotAffectGame() {

        Game originalGame = new GameBuilder().build();

        List<Person> participantList = new ArrayList<>(originalGame.getParticipants());


        participantList.add(TypicalPersons.AMY);

        assertFalse(originalGame.getParticipants().contains(TypicalPersons.AMY));
        assertEquals(2, originalGame.getParticipants().size()); // initialised with 2 pax
    }

    @Test
    public void constructor_nullDateTime_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Game(
                new Sport("volleyball"),
                null,
                LocationUtil.createLocation("402001")));
    }

    @Test
    public void withDifferentDateTime_createsNewGameWithDifferentDateTime() {
        LocalDateTime originalDateTime = TypicalGames.DATE_TIME_1;
        LocalDateTime newDateTime = TypicalGames.DATE_TIME_2;

        Game originalGame = new GameBuilder().withDateTime(DATE_TIME_1).build();
        Game newGame = new GameBuilder(originalGame).withDateTime(DATE_TIME_2).build();

        assertEquals(DATE_TIME_1, originalGame.getDateTime());
        assertEquals(DATE_TIME_2, newGame.getDateTime());
        assertFalse(originalGame.equals(newGame));
    }
    @Test
    public void getDateTime_returnsCorrectDateTime() {
        Game game = new GameBuilder(SOCCER).build();
        LocalDateTime expectedDateTime = TypicalGames.DATE_TIME_2;
        assertEquals(expectedDateTime, game.getDateTime());
    }

    @Test
    public void constructor_nullSport_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Game(
                null,
                VALID_DATETIME,
                LocationUtil.createLocation("402001")));
    }

    @Test
    public void getSport_returnsCorrectSport() {
        Game game = new GameBuilder(SOCCER).build();
        Sport expectedSport = new Sport("soccer");
        assertEquals(expectedSport, game.getSport());
    }

    @Test
    public void withDifferentLocation_createsNewGameWithDifferentLocation() {
        String originalPostalCode = "018906";
        String newPostalCode = "018935";

        Game originalGame = new GameBuilder().withLocation(originalPostalCode).build();
        Game newGame = new GameBuilder(originalGame).withLocation(newPostalCode).build();

        assertEquals(LocationUtil.createLocation(originalPostalCode), originalGame.getLocation());
        assertEquals(LocationUtil.createLocation(newPostalCode), newGame.getLocation());
        assertFalse(originalGame.equals(newGame));
    }

}
