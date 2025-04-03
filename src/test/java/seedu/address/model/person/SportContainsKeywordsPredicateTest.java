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


        assertTrue(firstPredicate.equals(firstPredicate));


        SportContainsKeywordsPredicate firstPredicateCopy =
                new SportContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));


        assertFalse(firstPredicate.equals(1));


        assertFalse(firstPredicate.equals(null));


        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_sportContainsKeywords_returnsTrue() {

        SportContainsKeywordsPredicate predicate =
                new SportContainsKeywordsPredicate(Collections.singletonList("soccer"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("basketball", "soccer"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("soccer", "basketball"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "basketball").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("basketball", "volleyball"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "volleyball").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("sOcCeR", "BaSketBaLl"));
        assertTrue(predicate.test(new PersonBuilder().withSports("soccer", "basketball").build()));
    }

    @Test
    public void test_sportDoesNotContainKeywords_returnsFalse() {

        SportContainsKeywordsPredicate predicate = new SportContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withSports("soccer").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("volleyball"));
        assertFalse(predicate.test(new PersonBuilder().withSports("soccer", "tennis").build()));


        predicate = new SportContainsKeywordsPredicate(Arrays.asList("Alice", "12345", "alice@email.com", "Main"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withSports("soccer").build()));


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
