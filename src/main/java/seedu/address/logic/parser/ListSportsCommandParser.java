package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListSportsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListSportsCommand object.
 */
public class ListSportsCommandParser implements Parser<ListSportsCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ListSportsCommand
     * and returns a ListSportsCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected forma
     */
    public ListSportsCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (!trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListSportsCommand.class.getSimpleName()
                    + " does not take any arguments."));
        }
        return new ListSportsCommand();
    }
}
