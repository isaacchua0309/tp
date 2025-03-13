package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_SPORT_FOOTBALL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddSportCommand;
import seedu.address.model.person.Sport;

public class AddSportCommandParserTest {

    private static final String SPORT_PREFIX = "s/";
    private AddSportCommandParser parser = new AddSportCommandParser();

    @Test
    public void parse_validArgs_returnsAddSportCommand() {
        // Valid index and sport name
        assertParseSuccess(parser, "1 s/Football", 
                new AddSportCommand(INDEX_FIRST_PERSON.getZeroBased(), new Sport(VALID_SPORT_FOOTBALL)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // No index specified
        assertParseFailure(parser, "s/Football", 
                String.format(AddSportCommand.MESSAGE_USAGE));

        // No sport specified
        assertParseFailure(parser, "1", 
                String.format(AddSportCommand.MESSAGE_USAGE));

        // Invalid index (not a number)
        assertParseFailure(parser, "abc s/Football", 
                String.format(AddSportCommand.MESSAGE_USAGE));

        // Missing sport prefix
        assertParseFailure(parser, "1 Football", 
                String.format(AddSportCommand.MESSAGE_USAGE));

        // Empty sport name
        assertParseFailure(parser, "1 s/", 
                String.format(AddSportCommand.MESSAGE_USAGE));
    }
} 