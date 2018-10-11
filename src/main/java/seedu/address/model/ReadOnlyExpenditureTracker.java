package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyExpenditureTracker {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Expenditure> getExpenditureList();

}
