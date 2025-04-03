package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.CreateSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddSportCommand object.
 */
public class CreateSportCommandParser implements Parser<CreateSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddSportCommand
     * and returns an AddSportCommand object for execution.
     * Expected format: INDEX s/SPORT_NAME
     *
     * @param userInput full user input string
     * @return an AddSportCommand object with the parsed index and sport
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public CreateSportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }

        // Expect the first token to be the index and the remaining to include the sport prefix.
        String[] tokens = trimmedInput.split("\\s+", 2);
        if (tokens.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }

        // The remaining string should start with the sport prefix, e.g., "s/"
        String sportToken = tokens[0].trim();
        if (!sportToken.startsWith(CliSyntax.PREFIX_SPORT.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }
        String sportName = sportToken.substring(CliSyntax.PREFIX_SPORT.getPrefix().length()).trim();
        if (sportName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }
        // ensure all sports are in lower case and prevent cases of "Badminton" and "badminton" to exist together
        return new CreateSportCommand(sportName.toLowerCase());
    }
}
