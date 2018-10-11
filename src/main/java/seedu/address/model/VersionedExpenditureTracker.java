package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code ExpenditureTracker} that keeps track of its own history.
 */
public class VersionedExpenditureTracker extends ExpenditureTracker {

    private final List<ReadOnlyExpenditureTracker> expenditureTrackerStateList;
    private int currentStatePointer;

    public VersionedExpenditureTracker(ReadOnlyExpenditureTracker initialState) {
        super(initialState);

        expenditureTrackerStateList = new ArrayList<>();
        expenditureTrackerStateList.add(new ExpenditureTracker(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code ExpenditureTracker} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        expenditureTrackerStateList.add(new ExpenditureTracker(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        expenditureTrackerStateList.subList(currentStatePointer + 1, expenditureTrackerStateList.size()).clear();
    }

    /**
     * Restores the address book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(expenditureTrackerStateList.get(currentStatePointer));
    }

    /**
     * Restores the address book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(expenditureTrackerStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has address book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has address book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < expenditureTrackerStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAddressBook)) {
            return false;
        }

        VersionedExpenditureTracker otherVersionedExpenditureTracker = (VersionedExpenditureTracker) other;

        // state check
        return super.equals(otherVersionedExpenditureTracker)
                && expenditureTrackerStateList.equals(otherVersionedExpenditureTracker.expenditureTrackerStateList)
                && currentStatePointer == otherVersionedExpenditureTracker.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of expenditureTrackerState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of expenditureTrackerState list, unable to redo.");
        }
    }
}
