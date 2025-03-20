package seedu.address.testutil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sport;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "1 STRAITS BOULEVARD SINGAPORE CHINESE CULTURAL CENTRE SINGAPORE";

    public static final String DEFAULT_POSTAL_CODE = "018906";

    public static final List<Sport> DEFAULT_SPORTS = List.of(new Sport("badminton"));

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;

    private String postalCode;
    private Set<Tag> tags;
    private List<Sport> sports;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        postalCode = DEFAULT_POSTAL_CODE;
        tags = new HashSet<>();
        sports = new ArrayList<>(DEFAULT_SPORTS);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        postalCode = personToCopy.getPostalCode();
        tags = new HashSet<>(personToCopy.getTags());
        sports = new ArrayList<>(personToCopy.getSports());
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code sports} into a {@code List<Sport>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withSports(String ... sports) {
        this.sports = SampleDataUtil.getSportList(sports);
        return this;
    }
    /**
     * Sets the {@code List<Sport>} of the {@code Person} that we are building.
     */
    public PersonBuilder withSportsTest(List<Sport> sports) {
        this.sports = new ArrayList<>(sports);
        return this;
    }

    /**
     * Adds the specified {@code Sport} to the {@code Person} that we are building.
     */
    public PersonBuilder withSport(String sportName) {
        this.sports.add(new Sport(sportName));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code PostalCode} of the {@code Person} that we are building.
     */
    public PersonBuilder withPostalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, postalCode, tags, sports);
    }
}
