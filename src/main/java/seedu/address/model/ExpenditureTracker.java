package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.expenditureinfo.ExpenditureList;
//import seedu.address.model.tag.Tag;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class ExpenditureTracker implements ReadOnlyExpenditureTracker {

    private final ExpenditureList expenditures;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        expenditures = new ExpenditureList();
    }

    public ExpenditureTracker() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public ExpenditureTracker(ReadOnlyExpenditureTracker toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setExpenditures(List<Expenditure> expenditures) {
        this.expenditures.setExpenditures(expenditures);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyExpenditureTracker newData) {
        requireNonNull(newData);

        setExpenditures(newData.getExpenditureList());
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addExpenditure(Expenditure e) {
        expenditures.add(e);
    }

    //// util methods

    @Override
    public String toString() {
        return expenditures.asUnmodifiableObservableList().size() + " expenditures";
        // TODO: refine later
    }

    @Override
    public ObservableList<Expenditure> getExpenditureList() {
        return expenditures.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureTracker // instanceof handles nulls
                && expenditures.equals(((ExpenditureTracker) other).expenditures));
    }

    @Override
    public int hashCode() {
        return expenditures.hashCode();
    }
}
