package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.HelloCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new HelloCommand object.
 */
public class HelloCommandParser implements Parser<HelloCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the HelloCommand
     * and returns a HelloCommand object for execution.
     *
     * @param args full user input string
     * @return a HelloCommand object with the parsed name
     * @throws ParseException if the user input does not conform the expected format
     */
    public HelloCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            return new HelloCommand(); // No name provided, use default greeting
        }
        
        return new HelloCommand(trimmedArgs); // Pass the name to HelloCommand
    }
} 