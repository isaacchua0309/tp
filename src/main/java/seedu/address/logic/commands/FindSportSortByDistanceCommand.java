package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.util.LocationUtil;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Location;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Finds and lists all persons in address book who play a sport contained in any of the argument keywords.
 * Then sorts them by distance from the address at the provided postal code.
 * Keyword matching is case-insensitive.
 */
public class FindSportSortByDistanceCommand extends FindSportCommand {

    public final String postalCode;
    public final Location locationToBeCompared;
    /**
     * Creates a FindSportCommand object to find persons who play certain sports
     *
     * @param predicate        test if a person has sports which are contained in user input lis
     * @param sportKeywordList user input list to check for valid sports
     */
    public FindSportSortByDistanceCommand(SportContainsKeywordsPredicate predicate,
                                          List<String> sportKeywordList, String postalCode) {
        super(predicate, sportKeywordList);
        this.postalCode = postalCode;
        this.locationToBeCompared = LocationUtil.createLocation(new Address("temp"), postalCode);
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        // Use Sport.isValidSport to validate each spor
        boolean hasInvalidSport = sportKeywordList.stream()
                .anyMatch(sport -> !Sport.isValidSport(sport));

        if (hasInvalidSport) {
            return new CommandResult(getInvalidSportMessage());
        }
        model.updateFilteredPersonList(predicate);
        model.sortFilteredPersonListByDistance(locationToBeCompared);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size()));
    }
}
