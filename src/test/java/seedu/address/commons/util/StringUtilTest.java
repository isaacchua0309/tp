package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

public class StringUtilTest {



    @Test
    public void isNonZeroUnsignedInteger() {


        assertFalse(StringUtil.isNonZeroUnsignedInteger(""));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("  "));


        assertFalse(StringUtil.isNonZeroUnsignedInteger("a"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("aaa"));


        assertFalse(StringUtil.isNonZeroUnsignedInteger("0"));


        assertTrue(StringUtil.isNonZeroUnsignedInteger("01"));


        assertFalse(StringUtil.isNonZeroUnsignedInteger("-1"));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("+1"));


        assertFalse(StringUtil.isNonZeroUnsignedInteger(" 10 "));
        assertFalse(StringUtil.isNonZeroUnsignedInteger("1 0"));


        assertFalse(StringUtil.isNonZeroUnsignedInteger(Long.toString(Integer.MAX_VALUE + 1)));


        assertTrue(StringUtil.isNonZeroUnsignedInteger("1"));
        assertTrue(StringUtil.isNonZeroUnsignedInteger("10"));
    }




    /*
     * Invalid equivalence partitions for word: null, empty, multiple words
     * Invalid equivalence partitions for sentence: null
     * The four test cases below test one invalid input at a time.
     */

    @Test
    public void containsWordIgnoreCase_nullWord_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase("typical sentence", null));
    }

    @Test
    public void containsWordIgnoreCase_emptyWord_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter cannot be empty", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "  "));
    }

    @Test
    public void containsWordIgnoreCase_multipleWords_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, "Word parameter should be a single word", ()
            -> StringUtil.containsWordIgnoreCase("typical sentence", "aaa BBB"));
    }

    @Test
    public void containsWordIgnoreCase_nullSentence_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.containsWordIgnoreCase(null, "abc"));
    }

    /*
     * Valid equivalence partitions for word:
     *   - any word
     *   - word containing symbols/numbers
     *   - word with leading/trailing spaces
     *
     * Valid equivalence partitions for sentence:
     *   - empty string
     *   - one word
     *   - multiple words
     *   - sentence with extra spaces
     *
     * Possible scenarios returning true:
     *   - matches first word in sentence
     *   - last word in sentence
     *   - middle word in sentence
     *   - matches multiple words
     *
     * Possible scenarios returning false:
     *   - query word matches part of a sentence word
     *   - sentence word matches part of the query word
     *
     * The test method below tries to verify all above with a reasonably low number of test cases.
     */

    @Test
    public void containsWordIgnoreCase_validInputs_correctResult() {


        assertFalse(StringUtil.containsWordIgnoreCase("", "abc"));
        assertFalse(StringUtil.containsWordIgnoreCase("    ", "123"));


        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bb"));
        assertFalse(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "bbbb"));


        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc", "Bbb"));
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bBb ccc@1", "CCc@1"));
        assertTrue(StringUtil.containsWordIgnoreCase("  AAA   bBb   ccc  ", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCase("Aaa", "aaa"));
        assertTrue(StringUtil.containsWordIgnoreCase("aaa bbb ccc", "  ccc  "));


        assertTrue(StringUtil.containsWordIgnoreCase("AAA bBb ccc  bbb", "bbB"));
    }



    /*
     * Equivalence Partitions: null, valid throwable objec
     */

    @Test
    public void getDetails_exceptionGiven() {
        assertTrue(StringUtil.getDetails(new FileNotFoundException("file not found"))
            .contains("java.io.FileNotFoundException: file not found"));
    }

    @Test
    public void getDetails_nullGiven_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> StringUtil.getDetails(null));
    }

}
