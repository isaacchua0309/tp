package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        List<String> trimmedKeywords = Arrays.asList("Alice", "Bob").stream()
                .map(String::trim)
                .collect(Collectors.toList());

        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(trimmedKeywords));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_keywordsWithExtraSpaces_returnsFindCommand() {
        // keywords with extra spaces that should be trimmed
        List<String> trimmedKeywords = Arrays.asList("Alice", "Bob").stream()
                .map(String::trim)
                .collect(Collectors.toList());

        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(trimmedKeywords));

        // Extra spaces in the keywords themselves
        assertParseSuccess(parser, "  Alice   Bob  ", expectedFindCommand);
    }
}
