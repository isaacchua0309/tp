package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGameLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGameLocationCommand object.
 */
public class EditGameLocationCommandParser implements Parser<EditGameLocationCommand> {

    @Override
    public EditGameLocationCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GAME_NAME, CliSyntax.PREFIX_POSTAL_CODE);

        if (!argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGameLocationCommand.MESSAGE_USAGE));
        }

        String gameIndexStr = argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).get();
        Index gameIndex;
        try {
            gameIndex = ParserUtil.parseIndex(gameIndexStr);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGameLocationCommand.MESSAGE_USAGE), pe);
        }

        String postalCodeStr = argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).get().trim();
        String locationStr;
        try {
            locationStr = ParserUtil.parsePostalCode(postalCodeStr);
        } catch (ParseException e) {
            throw new ParseException("Invalid postal code format.", e);
        }

        assert gameIndex.getZeroBased() >= 0 : "Parsed game index must be non-negative";

        return new EditGameLocationCommand(gameIndex.getZeroBased(), locationStr);
    }
}
