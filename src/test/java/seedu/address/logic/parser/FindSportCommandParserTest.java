package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindSportCommand;
import seedu.address.logic.commands.FindSportSortByDistanceCommand;
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

        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        FindSportCommand expectedCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords);


        assertParseSuccess(parser, " " + PREFIX_SPORT + "soccer "
                + PREFIX_SPORT + "cricket", expectedCommand);


        assertParseSuccess(parser, " \n " + PREFIX_SPORT + "soccer \n \t "
                + PREFIX_SPORT + "cricket  \t", expectedCommand);
    }

    /**
     * Tests parsing of valid arguments, expecting a correctly constructed FindSportSortByDistanceCommand.
     */
    @Test
    public void parse_validArgs_returnsFindSportSortByDistanceCommand() {

        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());


        FindSportSortByDistanceCommand expectedCommand = new FindSportSortByDistanceCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords, "018906");


        assertParseSuccess(parser, " " + PREFIX_POSTAL_CODE + "018906" + " " + PREFIX_SPORT + "soccer "
                + PREFIX_SPORT + "cricket", expectedCommand);


        assertParseSuccess(parser, " " + PREFIX_POSTAL_CODE + "018906" + " \n " + PREFIX_SPORT + "soccer \n \t "
                + PREFIX_SPORT + "cricket  \t", expectedCommand);
    }


    /**
     * Tests parsing of mixed-case arguments, expecting lowercase conversion.
     */
    @Test
    public void parse_mixedCaseArgs_returnsFindSportCommand() {

        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        FindSportCommand expectedCommand = new FindSportCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords);


        assertParseSuccess(parser, " " + PREFIX_SPORT + "SoCCer "
                + PREFIX_SPORT + "CRicKET", expectedCommand);
    }

    /**
     * Tests parsing of mixed-case arguments, expecting lowercase conversion and
     * a correctly constructed FindSportSortByDistanceCommand.
     */
    @Test
    public void parse_mixedCaseArgs_returnsFindSportSortByDistanceCommand() {

        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());


        FindSportSortByDistanceCommand expectedCommand = new FindSportSortByDistanceCommand(
                new SportContainsKeywordsPredicate(normalizedKeywords),
                normalizedKeywords, "018906");


        assertParseSuccess(parser, " " + PREFIX_POSTAL_CODE + "018906" + " " + PREFIX_SPORT + "soCCer "
                + PREFIX_SPORT + "criCkEt", expectedCommand);


        assertParseSuccess(parser, " " + PREFIX_POSTAL_CODE + "018906" + " \n " + PREFIX_SPORT + "soCcer \n \t "
                + PREFIX_SPORT + "CrIcket  \t", expectedCommand);
    }

    /**
     * Tests parsing of missing and invalid-postal code expecting parse failure.
     */
    @Test
    public void parse_invalidPostalCode_fails() {

        List<String> normalizedKeywords = Arrays.asList("soccer", "cricket").stream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());

        String missingExpectedMessage = "Postal code cannot be empty.";
        String invalidExpectedMessage = "Invalid postal code. Please enter a valid Singapore postal code.";


        assertParseFailure(parser, " " + PREFIX_POSTAL_CODE + " " + " \n " + PREFIX_SPORT + "soccer \n \t "
                + PREFIX_SPORT + "cricket \t", missingExpectedMessage);


        assertParseFailure(parser, " " + PREFIX_POSTAL_CODE + "000000" + " " + PREFIX_SPORT + "soccer "
                + PREFIX_SPORT + "cricket", invalidExpectedMessage);


    }
}
