package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.SportContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindSportCommand}.
 */
public class FindSportCommandTest {

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

        FindSportCommand findFirstCommand = new FindSportCommand(firstPredicate, Arrays.asList("soccer"));
        FindSportCommand findSecondCommand = new FindSportCommand(secondPredicate, Arrays.asList("rugby"));

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
     * Executes FindSportCommand with zero keywords and expects no persons found.
     */
    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(Collections.emptyList());
        FindSportCommand command = new FindSportCommand(predicate, Collections.emptyList());
        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    /**
     * Executes FindSportCommand with multiple valid keywords and expects multiple persons found.
     */
    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 2);
        SportContainsKeywordsPredicate predicate = preparePredicate("cricket tennis");
        FindSportCommand command = new FindSportCommand(predicate, Arrays.asList("cricket", "tennis"));
        expectedModel.updateFilteredPersonList(predicate);
        expectedModel.sortFilteredPersonListAlphabetically();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(BENSON, DANIEL), model.getFilteredPersonList());
    }

    /**
     * Executes FindSportCommand with an invalid sport keyword and expects failure message.
     */
    @Test
    public void execute_invalidSport_failure() {
        SportContainsKeywordsPredicate predicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("dodgeball"));
        FindSportCommand command = new FindSportCommand(predicate, Collections.singletonList("dodgeball"));
        String expectedMessage = FindSportCommand.MESSAGE_INVALID_SPORT;

        assertEquals(expectedMessage, command.execute(model).getFeedbackToUser());
    }

    @Test
    public void toStringMethod() {
        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(Arrays.asList("soccer"));
        FindSportCommand findCommand = new FindSportCommand(predicate, Arrays.asList("soccer"));
        String expected = FindSportCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }

    /**
     * Parses {@code userInput} into a {@code SportContainsKeywordsPredicate}.
     */
    private SportContainsKeywordsPredicate preparePredicate(String userInput) {
        return new SportContainsKeywordsPredicate(Arrays.asList(userInput.trim().split("\\s+")));
    }

    @Test void execute_isInCorrectOrder_afterFindSportSort() {
        List<String> sportKeywordList = Arrays.asList("soccer", "volleyball", "tennis", "cricket", "basketball");
        SportContainsKeywordsPredicate predicate =
                new SportContainsKeywordsPredicate(sportKeywordList);
        new FindSportSortByDistanceCommand(predicate, sportKeywordList, "018906").execute(model);
        FindSportCommand findCommand = new FindSportCommand(predicate, Arrays.asList("soccer"));
        String expected = FindSportCommand.class.getCanonicalName() + "{predicate=" + predicate + "}";
        assertEquals(expected, findCommand.toString());
    }
}
