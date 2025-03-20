package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddSportCommand;
import seedu.address.model.person.Sport;

public class AddSportCommandParserTest {
    private static final String VALID_SPORT = "soccer";
    private static final String INVALID_SPORT = "invalid-sport";

    private AddSportCommandParser parser = new AddSportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Valid spor
        AddSportCommand expectedCommand =
                new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), new Sport(VALID_SPORT));
        assertParseSuccess(parser, "1 s/soccer", expectedCommand);

        // With whitespace
        assertParseSuccess(parser, " 1   s/soccer   ", expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE);

        // Missing index
        assertParseFailure(parser, "s/soccer", expectedMessage);

        // Missing spor
        assertParseFailure(parser, "1", expectedMessage);

        // Missing sport prefix
        assertParseFailure(parser, "1 soccer", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid index
        assertParseFailure(parser, "0 s/soccer",
                MESSAGE_INVALID_INDEX);

        // Empty spor
        assertParseFailure(parser, "1 s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));

        // Invalid forma
        assertParseFailure(parser, "1s/soccer",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddSportCommand.MESSAGE_USAGE));
    }
}
