package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteGameCommand;
public class DeleteGameCommandParserTest {
    private DeleteGameCommandParser parser = new DeleteGameCommandParser();

    @Test
    public void parse_validIndex_success() {
        // Valid index
        DeleteGameCommand expectedCommand = new DeleteGameCommand(Index.fromOneBased(1));
        assertParseSuccess(parser, "1", expectedCommand);

        // With whitespace
        assertParseSuccess(parser, "  1  ", expectedCommand);
    }

    @Test
    public void parse_missingIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGameCommand.MESSAGE_USAGE);

        // Empty input
        assertParseFailure(parser, "", expectedMessage);

        // Whitespace
        assertParseFailure(parser, "   ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteGameCommand.MESSAGE_USAGE);

        // Invalid index - zero
        assertParseFailure(parser, "0", expectedMessage);

        // Invalid index - negative
        assertParseFailure(parser, "-1", expectedMessage);

        // Non-numeric input
        assertParseFailure(parser, "abc", expectedMessage);

        // Index with additional text
        assertParseFailure(parser, "1 extra", expectedMessage);
    }
}
