package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteSportCommand;

public class DeleteSportCommandParserTest {

    private DeleteSportCommandParser parser = new DeleteSportCommandParser();

    @Test
    public void parse_validIndex_success() {
        // Valid index
        DeleteSportCommand expectedCommand = new DeleteSportCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedCommand);

        // With whitespace
        assertParseSuccess(parser, "  1  ", expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_GLOBAL);

        // Empty inpu
        assertParseFailure(parser, "", expectedMessage);

        // Just whitespace
        assertParseFailure(parser, "   ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteSportCommand.MESSAGE_USAGE_GLOBAL);

        // Invalid index - zero
        assertParseFailure(parser, "0", expectedMessage);

        // Invalid index - negative
        assertParseFailure(parser, "-1", expectedMessage);

        // Non-numeric inpu
        assertParseFailure(parser, "abc", expectedMessage);

        // Index with additional tex
        assertParseFailure(parser, "1 extra", expectedMessage);
    }
}
