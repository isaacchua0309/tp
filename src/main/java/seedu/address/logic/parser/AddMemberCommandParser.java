package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddMemberCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AddMemberCommand object.
 */
public class AddMemberCommandParser implements Parser<AddMemberCommand> {

    @Override
    public AddMemberCommand parse(String args) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GAME_NAME, CliSyntax.PREFIX_NAME);


        if (!argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_NAME).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddMemberCommand.MESSAGE_USAGE));
        }


        String gameIndexStr = argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).get();
        Index gameIndex;
        try {
            gameIndex = ParserUtil.parseIndex(gameIndexStr);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddMemberCommand.MESSAGE_USAGE), pe);
        }


        String personName = argMultimap.getValue(CliSyntax.PREFIX_NAME).get().trim();

        return new AddMemberCommand(gameIndex, personName);
    }
}
