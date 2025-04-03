package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.DeleteSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteSportCommand object.
 */
public class DeleteSportCommandParser implements Parser<DeleteSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteSportCommand
     * and returns a DeleteSportCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected forma
     */
    public DeleteSportCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
            }

            // Parse the index which must be a positive integer
            if (!StringUtil.isNonZeroUnsignedInteger(trimmedArgs)) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
            }

            Index index = ParserUtil.parseIndex(trimmedArgs);
            return new DeleteSportCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE), pe);
        }
    }
}
