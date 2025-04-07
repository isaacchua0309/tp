package seedu.address.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.LocationUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.game.Game;
import seedu.address.model.person.Location;
import seedu.address.model.person.Person;
import seedu.address.model.person.Sport;

/**
 * JSON-friendly version of {@link Game}.
 */
public class JsonAdaptedGame {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Game's %s field is missing!";

    private final String sport;
    private final String dateTime;
    private final List<JsonAdaptedPerson> participants = new ArrayList<>();
    private final String location;
    /**
     * Constructs a {@code JsonAdaptedGame} with the given game details.
     */
    @JsonCreator
    public JsonAdaptedGame(@JsonProperty("sport") String sport,
                           @JsonProperty("dateTime") String dateTime,
                           @JsonProperty("participants") List<JsonAdaptedPerson> participants,
                            @JsonProperty("location") String location) {
        this.sport = sport;
        this.dateTime = dateTime;
        if (participants != null) {
            this.participants.addAll(participants);
        }
        this.location = location;
    }

    /**
     * Converts a given {@code Game} into this class for Jackson use.
     */
    public JsonAdaptedGame(Game source) {
        sport = source.getSport().sportName;
        dateTime = source.getDateTime().toString();
        participants.addAll(source.getParticipants().stream()
                .map(JsonAdaptedPerson::new)
                .collect(Collectors.toList()));
        location = source.getLocation().getPostalCode();
    }

    /**
     * Converts this JSON-friendly adapted game object into the model's {@code Game} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted game.
     */
    public Game toModelType() throws IllegalValueException {
        // Validate and create Sport
        if (sport == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "sport"));
        }
        if (!Sport.isValidSport(sport)) {
            throw new IllegalValueException(Sport.getMessageConstraints());
        }
        final Sport modelSport = new Sport(sport);

        // Validate and parse dateTime
        if (dateTime == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "dateTime"));
        }
        final LocalDateTime modelDateTime;
        try {
            modelDateTime = LocalDateTime.parse(dateTime);
        } catch (DateTimeParseException e) {
            throw new IllegalValueException("Invalid date/time format: " + dateTime);
        }

        if (location == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, "location"));
        }
        if (!LocationUtil.isValidPostalCode(location)) {
            throw new IllegalValueException(Sport.getMessageConstraints());
        }
        final Location modelLocation = LocationUtil.createLocation(location);

        // Convert participants to model types
        final List<Person> personList = new ArrayList<>();
        for (JsonAdaptedPerson participant : participants) {
            personList.add(participant.toModelType());
        }

        return new Game(modelSport, modelDateTime, modelLocation, personList);
    }
}
