package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book who play a sport contained in any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindSportCommand extends Command {

    public static final String COMMAND_WORD = "findsport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who play a sport contained in "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " badminton volleyball cricket";
    public static final String MESSAGE_INVALID_SPORT = "Invalid sport found. Allowed sports: " + Sport.VALID_SPORTS
            + "\nExample: " + COMMAND_WORD + " badminton volleyball cricket";


    private final SportContainsKeywordsPredicate predicate;
    private final List<String> sportList;

    /**
     * Creates a FindSportCommand object to find persons who play certain sports
     * @param predicate test if a person has sports which are contained in user input list
     * @param sportList user input list to check for valid sports
     */
    public FindSportCommand(SportContainsKeywordsPredicate predicate, List<String> sportList) {
        this.predicate = predicate;
        this.sportList = sportList;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Use Sport.isValidSport to validate each sport
        boolean hasInvalidSport = sportList.stream()
                .anyMatch(sport -> !Sport.isValidSport(sport));

        if (hasInvalidSport) {
            return new CommandResult(MESSAGE_INVALID_SPORT);
        }
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindSportCommand)) {
            return false;
        }

        FindSportCommand otherFindSportCommand = (FindSportCommand) other;
        return predicate.equals(otherFindSportCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
