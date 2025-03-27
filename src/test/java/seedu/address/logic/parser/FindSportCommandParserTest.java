package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSportCommand;
import seedu.address.model.person.SportContainsKeywordsPredicate;

public class FindSportCommandParserTest {

    private FindSportCommandParser parser = new FindSportCommandParser();

    /**
     * Tests parsing of empty arguments, expecting a ParseException.
     */
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSportCommand.MESSAGE_USAGE));
    }

    /**
     * Tests parsing of valid arguments, expecting a correctly constructed FindSportCommand.
     */
    @Test
    public void parse_validArgs_returnsFindSportCommand() {
        // no leading and trailing whitespaces
        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        FindSportCommand expectedCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords);

        assertParseSuccess(parser, " " + PREFIX_SPORT + "soccer "
                + PREFIX_SPORT + "cricket", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + PREFIX_SPORT + "soccer \n \t "
                + PREFIX_SPORT + "cricket  \t", expectedCommand);
    }


    /**
     * Tests parsing of mixed-case arguments, expecting lowercase conversion.
     */
    @Test
    public void parse_mixedCaseArgs_returnsFindSportCommand() {
        // Mixed case keywords (upper and lower case)
        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        FindSportCommand expectedCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords);

        assertParseSuccess(parser, " " + PREFIX_SPORT + "SoCCer "
                + PREFIX_SPORT + "CRicKET", expectedCommand);
    }
}
