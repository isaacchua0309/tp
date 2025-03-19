package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person}'s {@code Sports} matches any of the keywords given.
 */
public class SportContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;

    public SportContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        return keywords.stream()
                .anyMatch(keyword -> person.getSports().stream()
                        .anyMatch(sport -> StringUtil.containsWordIgnoreCase(sport.sportName, keyword)));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SportContainsKeywordsPredicate)) {
            return false;
        }

        SportContainsKeywordsPredicate otherSportContainsKeywordsPredicate = (SportContainsKeywordsPredicate) other;
        return keywords.equals(otherSportContainsKeywordsPredicate.keywords);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", keywords).toString();
    }
}
