package seedu.address.model.expenditureinfo;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//import seedu.address.model.person.exceptions.DuplicatePersonException;
//import seedu.address.model.person.exceptions.PersonNotFoundException;


public class ExpenditureList implements Iterable<Expenditure> {

    private final ObservableList<Expenditure> internalList = FXCollections.observableArrayList();

    public void add(Expenditure toAdd) {
        requireNonNull(toAdd);
        internalList.add(toAdd);
    }


    public void setExpenditures(ExpenditureList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */

    public void setExpenditures(List<Expenditure> expenditures) {
        requireAllNonNull(expenditures);
        internalList.setAll(expenditures);
    }


    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Expenditure> asUnmodifiableObservableList() {
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public Iterator<Expenditure> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExpenditureList // instanceof handles nulls
                && internalList.equals(((ExpenditureList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
