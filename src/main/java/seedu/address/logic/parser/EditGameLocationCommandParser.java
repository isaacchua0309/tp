package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditGameLocationCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditGameLocationCommand object.
 */
public class EditGameLocationCommandParser implements Parser<EditGameLocationCommand> {

    private static final Logger logger = Logger.getLogger(EditGameLocationCommandParser.class.getName());

    /**
     * Parses the given {@code String} of arguments in the context of the EditGameLocationCommand
     * and returns an EditGameLocationCommand object for execution.
     *
     * @param args The input arguments provided by the user.
     * @return An instance of EditGameLocationCommand with parsed arguments.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    @Override
    public EditGameLocationCommand parse(String args) throws ParseException {
        requireNonNull(args); // Defensive coding: ensure args is not null

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, CliSyntax.PREFIX_GAME_NAME, CliSyntax.PREFIX_POSTAL_CODE);

        if (!argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).isPresent()
                || !argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            // Logging: invalid command format detected
            logger.log(Level.WARNING, "Invalid command format: {0}", args);
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditGameLocationCommand.MESSAGE_USAGE));
        }

        String gameIndexStr = argMultimap.getValue(CliSyntax.PREFIX_GAME_NAME).get();
        Index gameIndex;
        try {
            gameIndex = ParserUtil.parseIndex(gameIndexStr); // Convert the string index to Index
        } catch (ParseException pe) {
            // Logging: exception handling for invalid index format
            logger.log(Level.WARNING, "Invalid game index format: {0}", gameIndexStr);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditGameLocationCommand.MESSAGE_USAGE), pe);
        }

        String postalCodeStr = argMultimap.getValue(CliSyntax.PREFIX_POSTAL_CODE).get().trim();
        String locationStr;
        try {
            locationStr = ParserUtil.parsePostalCode(postalCodeStr); // Convert postal code to location
        } catch (ParseException e) {
            // Logging: exception handling for invalid postal code format
            logger.log(Level.WARNING, "Invalid postal code format: {0}", postalCodeStr);
            throw new ParseException("Invalid postal code format.", e);
        }

        assert gameIndex.getZeroBased() >= 0 : "Parsed game index must be non-negative"; // Assertion check

        // Logging: successful parsing of arguments
        logger.log(Level.INFO, "Parsed EditGameLocationCommand with index: {0}, postalCode: {1}",
                new Object[]{gameIndex.getZeroBased(), locationStr});

        return new EditGameLocationCommand(gameIndex.getZeroBased(), locationStr);
    }

    /**
     * Helper method for defensive coding to ensure an object is not null.
     *
     * @param obj The object to check.
     * @throws NullPointerException if the object is null.
     */
    private void requireNonNull(Object obj) {
        if (obj == null) {
            throw new NullPointerException("Argument cannot be null");
        }
    }
}
