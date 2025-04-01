package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SPORT;

import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;


/**
 * Finds and lists all persons in address book who play a sport contained in any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindSportCommand extends Command {

    public static final String COMMAND_WORD = "findsport";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons who play a sport contained in "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: "
            + "[" + PREFIX_POSTAL_CODE + "POSTAL_CODE] "
            + PREFIX_SPORT + "SPORT_KEYWORD "
            + PREFIX_SPORT + "[MORE_SPORT_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_POSTAL_CODE + "018906 "
            + PREFIX_SPORT + "badminton "
            + PREFIX_SPORT + "volleyball "
            + PREFIX_SPORT + "cricket";
    public static final String MESSAGE_INVALID_SPORT = "Invalid sport found. Allowed sports: " + Sport.VALID_SPORTS
            + "\nExample: " + COMMAND_WORD
            + PREFIX_SPORT + "badminton "
            + PREFIX_SPORT + "volleyball "
            + PREFIX_SPORT + "cricket";

    protected final SportContainsKeywordsPredicate predicate;
    protected final List<String> sportKeywordList;

    /**
     * Creates a FindSportCommand object to find persons who play certain sports
     * @param predicate test if a person has sports which are contained in user input list
     * @param sportKeywordList user input list to check for valid sports
     */
    public FindSportCommand(SportContainsKeywordsPredicate predicate, List<String> sportKeywordList) {
        this.predicate = predicate;
        this.sportKeywordList = sportKeywordList;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Use Sport.isValidSport to validate each sport
        boolean hasInvalidSport = sportKeywordList.stream()
                .anyMatch(sport -> !Sport.isValidSport(sport));

        if (hasInvalidSport) {
            return new CommandResult(MESSAGE_INVALID_SPORT);
        }
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersonListAlphabetically();
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
