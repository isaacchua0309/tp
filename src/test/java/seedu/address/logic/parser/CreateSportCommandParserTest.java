package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateSportCommand;
import seedu.address.model.person.Sport;

public class CreateSportCommandParserTest {
    private static final String VALID_SPORT = "boxing";

    private CreateSportCommandParser parser;

    @BeforeEach
    public void setUp() {
        parser = new CreateSportCommandParser();

        // Ensure we have the default sports loaded for testing
        Sport.loadDefaultSports();
    }

    @Test
    public void parse_validArgs_returnsCreateSportCommand() {
        // Valid sport name
        CreateSportCommand expectedCommand = new CreateSportCommand(VALID_SPORT);
        assertParseSuccess(parser, "s/boxing", expectedCommand);

        // Valid sport name with whitespace
        assertParseSuccess(parser, "   s/boxing   ", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty inpu
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));

        // Missing sport prefix
        assertParseFailure(parser, "boxing",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));

        // Empty sport name
        assertParseFailure(parser, "s/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));

        // Sport prefix but with whitespace only
        assertParseFailure(parser, "s/   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateSportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitivity_success() {
        // Upper case sport name - should be converted to lowercase
        CreateSportCommand expectedCommand = new CreateSportCommand(VALID_SPORT);
        assertParseSuccess(parser, "s/BOXING", expectedCommand);

        // Mixed case sport name - should be converted to lowercase
        assertParseSuccess(parser, "s/BoXiNg", expectedCommand);
    }
}
