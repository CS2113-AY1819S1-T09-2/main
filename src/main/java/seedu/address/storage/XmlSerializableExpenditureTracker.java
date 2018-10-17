package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ExpenditureTracker;
import seedu.address.model.ReadOnlyExpenditureTracker;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * An Immutable AddressBook that is serializable to XML format
 */
@XmlRootElement(name = "expendituretracker")
public class XmlSerializableExpenditureTracker {

    //public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";

    @XmlElement
    private List<XmlAdaptedExpenditure> expenditures;

    /**
     * Creates an empty XmlSerializableExpenditureTracker.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableExpenditureTracker() {
        expenditures = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableExpenditureTracker(ReadOnlyExpenditureTracker src) {
        this();
        expenditures.addAll(src.getExpenditureList().stream()
                .map(XmlAdaptedExpenditure::new).collect(Collectors.toList()));
    }

    /**
     * Converts this expendituretracker into the model's {@code ExpenditureTracker} object.
     *
     * @throws IllegalValueException if there were any data constraints violated or duplicates in the
     * {@code XmlAdaptedExpenditure}.
     */
    public ExpenditureTracker toModelType() throws IllegalValueException {
        ExpenditureTracker expenditureTracker = new ExpenditureTracker();
        for (XmlAdaptedExpenditure e : expenditures) {
            Expenditure expenditure = e.toModelType();
            expenditureTracker.addExpenditure(expenditure);
        }
        return expenditureTracker;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableExpenditureTracker)) {
            return false;
        }
        return expenditures.equals(((XmlSerializableExpenditureTracker) other).expenditures);
    }
}
