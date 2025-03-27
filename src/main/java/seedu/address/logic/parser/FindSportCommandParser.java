package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindSportCommand;
import seedu.address.logic.commands.FindSportSortByDistanceCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindSportCommand object.
 */
public class FindSportCommandParser implements Parser<FindSportCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSportCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_POSTAL_CODE, PREFIX_SPORT);

        if (!arePrefixesPresent(argMultimap, PREFIX_SPORT) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSportCommand.MESSAGE_USAGE));
        }
        // ParserUtil.parseSports throws an exception if any sport is invalid, so there is no need to handle it again
        List<Sport> sports = ParserUtil.parseSports(argMultimap.getAllValues(PREFIX_SPORT));
        if (sports.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSportCommand.MESSAGE_USAGE));
        }

        // Convert the Sports objects to String keywords then
        // normalize the keywords by converting to lowercase
        List<String> sportKeywordList = sports.stream()
                .map(Sport::toString)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        //if postal code is provided, return FindSportSortByDistanceCommand
        // ParserUtil.parsePostalCode throws an exception if the provided postal code is invalid,
        // so there is no need to handle it again
        if (arePrefixesPresent(argMultimap, PREFIX_POSTAL_CODE)) {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_POSTAL_CODE);
            String postalCode = ParserUtil.parsePostalCode(argMultimap.getValue(PREFIX_POSTAL_CODE).get());
            return new FindSportSortByDistanceCommand(new SportContainsKeywordsPredicate(sportKeywordList),
                    sportKeywordList, postalCode);
        } else { //else return FindSportCommand as per usual
            return new FindSportCommand(new SportContainsKeywordsPredicate(sportKeywordList), sportKeywordList);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
