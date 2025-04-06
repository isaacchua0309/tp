package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EXCEED_SPORT_WORD_COUNT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;

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
     * @return an AddSportCommand object with the parsed index and spor
     * @throws ParseException if the user input does not conform the expected forma
     */
    @Override
    public CreateSportCommand parse(String userInput) throws ParseException {
        String trimmedInput = userInput.trim();
        if (trimmedInput.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_SPORT);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SPORT);

        String[] tokens = trimmedInput.split("\\s+", 10);
        if (tokens.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }


        StringBuilder sportTokenBuilder = new StringBuilder(tokens[0]);
        for (int i = 1; i < tokens.length; i++) {
            sportTokenBuilder.append(" ").append(tokens[i]);
        }
        String sportToken = sportTokenBuilder.toString().trim();
        if (!sportToken.startsWith(CliSyntax.PREFIX_SPORT.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }
        String sportName = sportToken.substring(CliSyntax.PREFIX_SPORT.getPrefix().length()).trim();
        if (sportName.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
        }

        if (sportName.replaceAll("\\s", "").length() > 30) {
            throw new ParseException(String.format(MESSAGE_EXCEED_SPORT_WORD_COUNT));
        }

        return new CreateSportCommand(sportName.toLowerCase());
    }
}
