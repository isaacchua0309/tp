package seedu.address.model.util;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Sport;
import seedu.address.model.person.SportList;
import seedu.address.model.tag.Tag;


/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"), "398362",
                    getTagSet("friends"), getSportListObject("badminton")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"), "018906",
                    getTagSet("colleagues", "friends"), getSportListObject("volleyball")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"), "018907",
                    getTagSet("neighbours"), getSportListObject("cricket")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"), "560436",
                    getTagSet("family"), getSportListObject("rugby")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"), "018910",
                    getTagSet("classmates"), getSportListObject("hockey")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"), "018916",
                    getTagSet("colleagues"), getSportListObject("soccer")),
            new Person(new Name("Darren Teo"), new Phone("97702811"), new Email("darrenteo@example.com"),
                    new Address("Darren's Home in the east"), "417451",
                    getTagSet("demoSubject1"), getSportListObject("basketball")),
            new Person(new Name("Isaac Chua"), new Phone("87994339"), new Email("cheerfuldino@example.com"),
                    new Address("Isaac's Hostel in the west"), "119618",
                    getTagSet("demoSubject2"), getSportListObject("basketball")),
            new Person(new Name("Lucas Goh"), new Phone("94875091"), new Email("lucasgoh@example.com"),
                    new Address("Lucas' Home in the north"), "579506",
                    getTagSet("demoSubject3"), getSportListObject("basketball")),
            new Person(new Name("Noah Ang"), new Phone("97723485"), new Email("noahang@example.com"),
                    new Address("Noah's Home in central Singapore"), "570154",
                    getTagSet("demoSubject4"), getSportListObject("basketball")),
            new Person(new Name("Somneel Saha"), new Phone("96736925"), new Email("somneelsaha@example.com"),
                        new Address("Somneel's Hostel in the west"), "119617",
                        getTagSet("demoSubject5"), getSportListObject("basketball"))

        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a sport list containing the list of strings given.
     */
    public static List<Sport> getSportList(String... strings) {
        return Arrays.stream(strings)
                .map(Sport::new)
                .collect(Collectors.toList());
    }

    /**
     * Returns a SportList object containing the list of strings given.
     */
    public static SportList getSportListObject(String... strings) {
        return new SportList(getSportList(strings));
    }
}
