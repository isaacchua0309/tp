package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedSport.MESSAGE_INVALID;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Sport;

public class JsonAdaptedSportTest {
    private static final String VOLLEYBALL = "volleyball";
    @Test
    public void toModelType_validSportDetails_returnsSport() throws Exception {
        JsonAdaptedSport sport = new JsonAdaptedSport(VOLLEYBALL);
        assertEquals(new Sport(VOLLEYBALL), sport.toModelType());
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedSport sport = new JsonAdaptedSport((String) null);
        String expectedMessage = String.format(MESSAGE_INVALID);
        assertThrows(IllegalValueException.class, expectedMessage, sport::toModelType);
    }
}
