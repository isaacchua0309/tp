package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedGame.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalGames.VOLLEYBALL;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Sport;
import seedu.address.testutil.TypicalPersons;

public class JsonAdaptedGameTest {
    private static final String INVALID_DATETIME = "2025-04-04";
    private static final String INVALID_LOCATION = "123456";

    private static final String VOLLEYBALL_NAME = "volleyball";
    private static final String VALID_DATETIME = "2025-04-04T15:00:00";
    private static final String VALID_LOCATION = "259366";
    private static final List<JsonAdaptedPerson> VALID_PERSONS = List.of(TypicalPersons.getTypicalPersons().get(0),
            TypicalPersons.getTypicalPersons().get(1)).stream()
            .map(JsonAdaptedPerson::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validGameDetails_returnsGame() throws Exception {
        JsonAdaptedGame game = new JsonAdaptedGame(VOLLEYBALL_NAME, VALID_DATETIME, VALID_PERSONS, VALID_LOCATION);
        assertEquals(VOLLEYBALL, game.toModelType());
    }

    @Test
    public void toModelType_invalidDateTime_throwsIllegalValueException() {
        JsonAdaptedGame game = new JsonAdaptedGame(VOLLEYBALL_NAME, INVALID_DATETIME, VALID_PERSONS, VALID_LOCATION);
        String expectedMessage = String.format("Invalid date/time format: %s", INVALID_DATETIME);
        assertThrows(IllegalValueException.class, expectedMessage, game::toModelType);
    }

    @Test
    public void toModelType_nullDateTime_throwsIllegalValueException() {
        JsonAdaptedGame game = new JsonAdaptedGame(VOLLEYBALL_NAME, null, VALID_PERSONS, VALID_LOCATION);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "dateTime");
        assertThrows(IllegalValueException.class, expectedMessage, game::toModelType);
    }

    @Test
    public void toModelType_invalidLocation_throwsIllegalValueException() {
        JsonAdaptedGame game = new JsonAdaptedGame(VOLLEYBALL_NAME, VALID_DATETIME, VALID_PERSONS, INVALID_LOCATION);
        String expectedMessage = Sport.getMessageConstraints();
        assertThrows(IllegalValueException.class, expectedMessage, game::toModelType);
    }

    @Test
    public void toModelType_nullLocation_throwsIllegalValueException() {
        JsonAdaptedGame game = new JsonAdaptedGame(VOLLEYBALL_NAME, VALID_DATETIME, VALID_PERSONS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "location");
        assertThrows(IllegalValueException.class, expectedMessage, game::toModelType);
    }
}
