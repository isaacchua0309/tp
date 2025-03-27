package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.FindSportCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindSportCommand object.
 */
public class FindSportCommandParser implements Parser<FindSportCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected forma
     */
    public FindSportCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSportCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        // Normalize the keywords by converting to lowercase
        List<String> sportList = Arrays.stream(nameKeywords)
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        return new FindSportCommand(new SportContainsKeywordsPredicate(sportList), sportList);
    }
}
