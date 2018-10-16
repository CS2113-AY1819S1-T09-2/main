package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.expenditureinfo.Expenditure;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Task;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedAddressBook versionedAddressBook;
    private final VersionedExpenditureTracker versionedExpenditureTracker;
    private final FilteredList<Person> filteredPersons;

    private final FilteredList<Expenditure> filteredExpenditures;

    private final FilteredList<Task> filteredTasks;


    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook,
                        ReadOnlyExpenditureTracker expenditureTracker, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, expenditureTracker, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        versionedAddressBook = new VersionedAddressBook(addressBook);
        versionedExpenditureTracker = new VersionedExpenditureTracker(expenditureTracker);
        filteredPersons = new FilteredList<>(versionedAddressBook.getPersonList());

        filteredExpenditures = new FilteredList<>(versionedExpenditureTracker.getExpenditureList());

        filteredTasks = new FilteredList<>(versionedAddressBook.getTaskList());
    }

    public ModelManager() {
        this(new AddressBook(), new ExpenditureTracker(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyExpenditureTracker getExpenditureTracker() {
        return versionedExpenditureTracker;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public boolean hasExpenditure(Expenditure expenditure) {
        requireNonNull(expenditure);
        return versionedExpenditureTracker.hasExpenditure(expenditure);
    }
    
    @Override
    public boolean hasTask(Task task) {
        requireNonNull(task);
        return versionedAddressBook.hasTask(task);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteExpenditure(Expenditure target) {
        versionedExpenditureTracker.removeExpenditure(target);
    }
    
    @Override
    public void deleteTask(Task target) {
        versionedAddressBook.removeTask(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addTask(Task task) {
        versionedAddressBook.addTask(task);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addExpenditure(Expenditure expenditure) {
        versionedExpenditureTracker.addExpenditure(expenditure);
        updateFilteredExpenditureList(PREDICATE_SHOW_ALL_EXPENDITURES);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void updateExpenditure(Expenditure target, Expenditure editedExpenditure) {
        requireAllNonNull(target, editedExpenditure);

        versionedExpenditureTracker.updateExpenditure(target, editedExpenditure);
        indicateAddressBookChanged();
    }
  
    @Override
    public void updateTask(Task target, Task editedTask) {
        requireAllNonNull(target, editedTask);

        versionedAddressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTag(Tag tag) {
        versionedAddressBook.removeTag(tag);
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public ObservableList<Expenditure> getFilteredExpenditureList() {
        return FXCollections.unmodifiableObservableList(filteredExpenditures);
    }
  
    @Override
    public ObservableList<Task> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
  
    @Override
    public void updateFilteredExpenditureList(Predicate<Expenditure> predicate) {
        requireNonNull(predicate);
        filteredExpenditures.setPredicate(predicate);
    }

    @Override
    public void updateFilteredTaskList(Predicate<Task> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
