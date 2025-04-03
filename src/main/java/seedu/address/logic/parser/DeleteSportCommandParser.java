package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Sport;

/**
 * Parses input arguments and creates a new DeleteSportCommand object.
 */
public class DeleteSportCommandParser implements Parser<DeleteSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSportCommand
     * and returns a DeleteSportCommand object for execution.
     * DeleteSport has 2 variants, "deletesport {INDEX}" and "deletesport {index} s/SPORT_NAME}
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteSportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args,
                CliSyntax.PREFIX_SPORT
        );
        // removed this conditional check, might have caused some errors in testing !argMultimap.getPreamble().isEmpty()
        if (!argMultimap.getValue(CliSyntax.PREFIX_SPORT).isPresent()) {

            try {
                String trimmedArgs = args.trim();
                if (trimmedArgs.isEmpty()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_GLOBAL));
                }

                // Parse the index which must be a positive integer
                if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_GLOBAL));
                }

                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new DeleteSportCommand(index);
            } catch (ParseException pe) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_GLOBAL), pe);
            }
        } else {
            String trimmedInput = args.trim();
            if (trimmedInput.isEmpty()) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_PERSON));
            }

            // Expect the first token to be the index and the remaining to include the sport prefix.
            String[] tokens = trimmedInput.split("\\s+", 2);
            if (tokens.length < 2) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_PERSON));
            }

            // Parse the index (1-based index from user)
            Index index = ParserUtil.parseIndex(tokens[0]);

            // The remaining string should start with the sport prefix, e.g., "s/"
            String sportToken = tokens[1].trim();
            if (!sportToken.startsWith(CliSyntax.PREFIX_SPORT.getPrefix())) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_PERSON));
            }
            String sportName = sportToken.substring(CliSyntax.PREFIX_SPORT.getPrefix().length()).trim();
            if (sportName.isEmpty()) {
                throw new ParseException(String.format(
                        MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_PERSON));
            }

            Sport sport = new Sport(sportName);
            return new DeleteSportCommand(index, sport);
        }
    }
}
