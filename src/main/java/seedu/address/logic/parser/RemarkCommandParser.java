package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Remark;

/**
 * Parses input arguments and creates a new RemarkCommand object.
 */
public class RemarkCommandParser implements Parser<RemarkCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the RemarkCommand
     * and returns a RemarkCommand object for execution.
     *
     * @param args full user input string
     * @return a RemarkCommand object with the parsed index and remark
     * @throws ParseException if the user input does not conform the expected format
     */
    public RemarkCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        // Expecting the first token to be the index and the remaining part to contain the remark
        String[] tokens = trimmedArgs.split("\\s+", 2);
        if (tokens.length < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }

        Index index;
        try {
            index = ParserUtil.parseIndex(tokens[0]);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE), pe);
        }

        // The remaining string should start with the remark prefix, e.g., "r/"
        String remarkInput = tokens[1].trim();
        if (!remarkInput.startsWith(CliSyntax.PREFIX_REMARK.getPrefix())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));
        }
        String remarkContent = remarkInput.substring(CliSyntax.PREFIX_REMARK.getPrefix().length()).trim();

        Remark remark = ParserUtil.parseRemark(remarkContent);
        return new RemarkCommand(index.getZeroBased(), remark);
    }
}
