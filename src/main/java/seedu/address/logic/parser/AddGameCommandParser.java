package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.AddGameCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.game.Game;

/**
 * Parses input arguments and creates a new AddGameCommand object.
 */
public class AddGameCommandParser implements Parser<AddGameCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddGameCommand
     * and returns an AddGameCommand object for execution.
     *
     * Expected arguments:
     *   g/SPORT_NAME
     *   dt/2021-09-20T15:00:00
     *   pc/123456
     *
     * Example usage:
     *   addgame g/Soccer dt/2021-09-20T15:00:00 pc/123456
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public AddGameCommand parse(String args) throws ParseException {
        // Tokenize with the new location prefix
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                CliSyntax.PREFIX_GAME_NAME,
                CliSyntax.PREFIX_DATETIME,
                CliSyntax.PREFIX_POSTAL_CODE
        );

        // Check required prefixes
        if (!argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_DATETIME).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddGameCommand.MESSAGE_USAGE)
            );
        }

        // Extract raw strings from user input
        String gameNameStr = argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).get();
        String dateTimeStr = argMultimap.getValue(CliSyntax.PREFIX_DATETIME).get();
        String locationStr = argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).get();

        // Convert to domain objects (Sport, LocalDateTime, Location) and build Game
        Game game = ParserUtil.parseGame(gameNameStr, dateTimeStr, locationStr);

        return new AddGameCommand(game);
    }
}
