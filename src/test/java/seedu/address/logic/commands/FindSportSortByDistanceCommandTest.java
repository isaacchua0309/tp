package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.LocationUtil;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Address;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSportCommand}.
 */
public class FindSportSortByDistanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    /**
     * Tests equality conditions of FindSportCommand objects.
     */
    @Test
    public void equals() {
        SportContainsKeywordsPredicate firstPredicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("soccer"));
        SportContainsKeywordsPredicate secondPredicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("rugby"));

        FindSportSortByDistanceCommand findFirstCommand = new FindSportSortByDistanceCommand(firstPredicate,
                Arrays.asList("soccer"), "018906");
        FindSportSortByDistanceCommand findSecondCommand = new FindSportSortByDistanceCommand(secondPredicate,
                Arrays.asList("rugby"), "018907");

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindSportCommand findFirstCommandCopy = new FindSportCommand(firstPredicate, Arrays.asList("soccer"));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different sports -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Executes FindSportSortByDistanceCommand with zero keywords and expects no persons found.
     */
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(Collections.emptyList());
        FindSportSortByDistanceCommand command = new FindSportSortByDistanceCommand(predicate, Collections.emptyList(), "018906");
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Executes FindSportSortByDistanceCommand with multiple valid keywords and expects multiple persons found.
     * The expected model is updated with the filtered list of persons and sorted by distance.
     */
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 4);
        SportContainsKeywordsPredicate predicate = preparePredicate("volleyball cricket basketball rugby");
        FindSportSortByDistanceCommand command = new FindSportSortByDistanceCommand(predicate,
                Arrays.asList("volleyball", "cricket", "basketball", "rugby"), "018916");
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.sortFilteredPersonListByDistance(LocationUtil
                .createLocation(new Address("temp"), "018916"));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ELLE, ALICE, CARL, BENSON), model.getFilteredPersonList());
        //sorted by distance upon checking using online postal code distance calculator
    }

    /**
     * Executes FindSportCommand with an invalid sport keyword and expects failure message.
     */
    @Test
    public void execute_invalidSport_failure() {
        SportContainsKeywordsPredicate predicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("dodgeball"));
        FindSportCommand command = new FindSportSortByDistanceCommand(predicate, Collections.singletonList("dodgeball"),
                "018906");
        String expectedMessage = FindSportCommand.MESSAGE_INVALID_SPORT;

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
    }

    /**
     * Parses {@code userInput} into a {@code SportContainsKeywordsPredicate}.
     */
    private SportContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SportContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }
}
