package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ExpenditureTracker;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenditureTracker;
import seedu.address.model.expenditureinfo.*;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends")),
            new Person(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                    getTagSet("colleagues", "friends")),
            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours")),
            new Person(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("family")),
            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates")),
            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Address("Blk 45 Aljunied Street 85, #11-31"),
                    getTagSet("colleagues"))
        };
    }

    public static Expenditure[] getSampleExpenditures() {
        return new Expenditure[] {
            new Expenditure(new Description("Mcdonalds McFlurry"), new Date("01-01-2018"), new Money("8.5"), new Category("Food")),
            new Expenditure(new Description("Chicken rice"), new Date("02-01-2018"), new Money("4.2"), new Category("Food")),
            new Expenditure(new Description("iPhoneX"), new Date("03-01-2018"), new Money("1680"), new Category("Electronics")),
            new Expenditure(new Description("iPhoneXS MAX"), new Date("13-10-2018"), new Money("2135"), new Category("Electronics")),
            new Expenditure(new Description("Aircon"), new Date("14-10-2018"), new Money("799.00"), new Category("Furniture")),
            new Expenditure(new Description("Nike hoodie"), new Date("15-10-2018"), new Money("135"), new Category("Clothing"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    public static ReadOnlyExpenditureTracker getSampleExpenditureTracker() {
        ExpenditureTracker sampleEt = new ExpenditureTracker();
        for (Expenditure sampleExpenditure : getSampleExpenditures()) {
            sampleEt.addExpenditure(sampleExpenditure);
        }
        return sampleEt;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
