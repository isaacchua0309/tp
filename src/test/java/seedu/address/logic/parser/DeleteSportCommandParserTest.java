package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteSportCommand;
import seedu.address.model.person.Sport;

public class DeleteSportCommandParserTest {

    private static final String VALID_SPORT = "tennis";

    private DeleteSportCommandParser parser = new DeleteSportCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        // Valid Entry
        DeleteSportCommand expectedCommand =
                new DeleteSportCommand(INDEX_FIRST_PERSON, new Sport(VALID_SPORT));
        assertParseSuccess(parser, "1 s/tennis", expectedCommand);

        // With whitespace
        assertParseSuccess(parser, " 1    s/  tennis  ", expectedCommand);

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE);

        // Missing index
        assertParseFailure(parser, "s/soccer", expectedMessage);

        // Missing sport
        assertParseFailure(parser, "1", expectedMessage);

        // Missing sport prefix
        assertParseFailure(parser, "1 soccer", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid index
        assertParseFailure(parser, "0 s/soccer",
                MESSAGE_INVALID_INDEX);

        assertParseFailure(parser, "-1 s/soccer",
                MESSAGE_INVALID_INDEX);

        // Empty sport
        assertParseFailure(parser, "1 s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));

        // Invalid format
        assertParseFailure(parser, "1s/soccer",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE));
    }
}
