package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sport;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";

    private static final String INVALID_POSTAL_CODE = "999999"; // Non-existent postal code
    private static final String INVALID_POSTAL_CODE_FORMAT = "12AB34"; // Invalid forma
    private static final String INVALID_POSTAL_CODE_TOO_SHORT = "12345"; // Too shor
    private static final String INVALID_POSTAL_CODE_TOO_LONG = "1234567"; // Too long
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";


    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS = "123 Main Street #0505";

    private static final String VALID_POSTAL_CODE = "018906";
    private static final String VALID_POSTAL_CODE_2 = "018935";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

    private static final String WHITESPACE = " \t\r\n";

    // Tests for sport parsing
    private static final String INVALID_SPORT = "cricket123";
    private static final String VALID_SPORT_1 = "soccer";
    private static final String VALID_SPORT_2 = "basketball";

    @Test
    public void parseIndex_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseIndex("10 a"));
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, ()
            -> ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parsePostalCode_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePostalCode((String) null));
    }

    @Test
    public void parsePostalCode_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(INVALID_POSTAL_CODE));
    }

    @Test
    public void parsePostalCode_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(INVALID_POSTAL_CODE_FORMAT));
    }

    @Test
    public void parsePostalCode_tooShort_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(INVALID_POSTAL_CODE_TOO_SHORT));
    }

    @Test
    public void parsePostalCode_tooLong_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(INVALID_POSTAL_CODE_TOO_LONG));
    }

    @Test
    public void parsePostalCode_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(""));
    }

    @Test
    public void parsePostalCode_validValueWithoutWhitespace_returnsPostalCode() throws Exception {
        String expectedPostalCode = VALID_POSTAL_CODE;
        assertEquals(expectedPostalCode, ParserUtil.parsePostalCode(VALID_POSTAL_CODE));
    }

    @Test
    public void parsePostalCode_validValueWithWhitespace_returnsTrimmedPostalCode() throws Exception {
        String postalCodeWithWhitespace = WHITESPACE + VALID_POSTAL_CODE + WHITESPACE;
        String expectedPostalCode = VALID_POSTAL_CODE;
        assertEquals(expectedPostalCode, ParserUtil.parsePostalCode(postalCodeWithWhitespace));
    }

    @Test
    public void parsePostalCode_anotherValidValue_returnsPostalCode() throws Exception {
        String expectedPostalCode = VALID_POSTAL_CODE_2;
        assertEquals(expectedPostalCode, ParserUtil.parsePostalCode(VALID_POSTAL_CODE_2));
    }

    @Test
    public void parsePostalCode_multipleWhitespacesBetween_throwsParseException() {
        String postalCodeWithInternalSpaces = "018 906";
        assertThrows(ParseException.class, () -> ParserUtil.parsePostalCode(postalCodeWithInternalSpaces));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG)));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseSport_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSport((String) null));
    }

    @Test
    public void parseSport_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseSport(INVALID_SPORT));
    }

    @Test
    public void parseSport_validValueWithoutWhitespace_returnsSport() throws Exception {
        Sport expectedSport = new Sport(VALID_SPORT_1);
        assertEquals(expectedSport, ParserUtil.parseSport(VALID_SPORT_1));
    }

    @Test
    public void parseSport_validValueWithWhitespace_returnsTrimmedSport() throws Exception {
        String sportWithWhitespace = WHITESPACE + VALID_SPORT_1 + WHITESPACE;
        Sport expectedSport = new Sport(VALID_SPORT_1);
        assertEquals(expectedSport, ParserUtil.parseSport(sportWithWhitespace));
    }

    @Test
    public void parseSports_collectionWithValidSports_returnsSportList() throws Exception {
        List<String> sportStrings = Arrays.asList(VALID_SPORT_1, VALID_SPORT_2);
        List<Sport> expectedSportList = new ArrayList<>();
        expectedSportList.add(new Sport(VALID_SPORT_1));
        expectedSportList.add(new Sport(VALID_SPORT_2));

        List<Sport> actualSportList = ParserUtil.parseSports(sportStrings);
        assertEquals(expectedSportList.size(), actualSportList.size());
        assertTrue(actualSportList.containsAll(expectedSportList));
    }

    @Test
    public void parseSports_emptyCollection_returnsEmptyList() throws Exception {
        assertTrue(ParserUtil.parseSports(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseSports_collectionWithInvalidSports_throwsParseException() {
        assertThrows(ParseException.class, () ->
                ParserUtil.parseSports(Arrays.asList(VALID_SPORT_1, INVALID_SPORT)));
    }
}
