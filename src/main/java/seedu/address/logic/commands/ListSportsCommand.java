package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.model.Model;
import seedu.address.model.person.Sport;

/**
 * Lists all available sports in alphabetical order with indices.
 */
public class ListSportsCommand extends Command {

    public static final String COMMAND_WORD = "listsports";

    public static final String MESSAGE_SUCCESS = "Listed all available sports:";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Get the sorted list of sports
        List<String> sortedSports = Sport.getSortedValidSports();

        // Build a formatted string with indexed sports
        StringBuilder result = new StringBuilder(MESSAGE_SUCCESS + "\n");
        for (int i = 0; i < sortedSports.size(); i++) {
            result.append(String.format("%d. %s\n", i + 1, sortedSports.get(i)));
        }

        return new CommandResult(result.toString());
    }
}
