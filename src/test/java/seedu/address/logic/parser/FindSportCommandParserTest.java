package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

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
        FindSportCommand expectedCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(Arrays.asList("soccer", "cricket")),
                Arrays.asList("soccer", "cricket"));

        assertParseSuccess(parser, "soccer cricket", expectedCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n soccer \n \t cricket  \t", expectedCommand);
    }

}
