package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.testutil.PersonBuilder;

public class SportContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("soccer");
        List<String> secondPredicateKeywordList = Arrays.asList("soccer", "basketball");

        SportContainsKeywordsPredicate firstPredicate = new SportContainsKeywordsPredicate(firstPredicateKeywordList);
        SportContainsKeywordsPredicate secondPredicate = new SportContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        SportContainsKeywordsPredicate firstPredicateCopy =
                new SportContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different predicate -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_sportContainsKeywords_returnsTrue() {
        // One keyword
        SportContainsKeywordsPredicate predicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("soccer"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer").build()));

        // Multiple keywords, matches one
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("basketball", "soccer"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer").build()));

        // Multiple keywords, matches multiple
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("soccer", "basketball"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "basketball").build()));

        // Only one matching keyword
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("basketball", "volleyball"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "volleyball").build()));

        // Mixed-case keywords
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("sOcCeR", "BaSketBaLl"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "basketball").build()));
    }

    @Test
    public void test_sportDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSports("soccer").build()));

        // Non-matching keyword
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("volleyball"));
        assertFalse(predicate.test(new PersonBuilder().withSports("soccer", "tennis").build()));

        // Keywords match name, phone, email and address, but does not match any sport
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withSports("soccer").build()));

        // Empty sport list
        predicate = new SportContainsKeywordsPredicate(Arrays.asList("soccer"));
        Person personWithoutSports = new PersonBuilder().withSports().build();
        assertFalse(predicate.test(personWithoutSports));
    }

    @Test
    public void toStringMethod() {
        List<String> keywords = List.of("soccer", "basketball");
        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(keywords);

        String expected = new ToStringBuilder(predicate).add("keywords", keywords).toString();
        assertEquals(expected, predicate.toString());
    }
}
