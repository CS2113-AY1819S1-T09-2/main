package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Represents a selection change in the Person List Panel
 */
public class ExpenditurePanelSelectionChangedEvent extends BaseEvent {


    private final Expenditure newSelection;

    public ExpenditurePanelSelectionChangedEvent(Expenditure newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    public Expenditure getNewSelection() {
        return newSelection;
    }
}
