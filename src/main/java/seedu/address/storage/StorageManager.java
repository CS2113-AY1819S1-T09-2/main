package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TodoListChangedEvent;
import seedu.address.commons.events.storage.DataSavingExceptionEvent;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyExpenditureTracker;
import seedu.address.model.ReadOnlyTodoList;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook, TodoList data in local storage.
 */
public class StorageManager extends ComponentManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private AddressBookStorage addressBookStorage;
    private TodoListStorage todoListStorage;
    private UserPrefsStorage userPrefsStorage;


    public StorageManager(AddressBookStorage addressBookStorage, TodoListStorage todoListStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.todoListStorage = todoListStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(UserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getAddressBookFilePath() {
        return addressBookStorage.getAddressBookFilePath();
    }

    @Override
    public Path getExpenditureTrackerFilePath() {
        return addressBookStorage.getExpenditureTrackerFilePath();
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataConversionException, IOException {
        return readAddressBook(addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read AB data from file: " + filePath);
        return addressBookStorage.readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyExpenditureTracker> readExpenditureTracker() throws DataConversionException, IOException {
        return readExpenditureTracker(addressBookStorage.getExpenditureTrackerFilePath());
    }

    @Override
    public Optional<ReadOnlyExpenditureTracker> readExpenditureTracker(Path expenditureFilePath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + expenditureFilePath);
        return addressBookStorage.readExpenditureTracker(expenditureFilePath);
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, addressBookStorage.getAddressBookFilePath());
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to AB data file: " + filePath);
        addressBookStorage.saveAddressBook(addressBook, filePath);
    }


    // ================ TodoList methods ==============================

    @Override
    public Path getTodoListFilePath() {
        return todoListStorage.getTodoListFilePath();
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList() throws DataConversionException, IOException {
        return readTodoList(todoListStorage.getTodoListFilePath());
    }

    @Override
    public Optional<ReadOnlyTodoList> readTodoList(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read TDL data from file: " + filePath);
        return todoListStorage.readTodoList(filePath);
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList) throws IOException {
        saveTodoList(todoList, todoListStorage.getTodoListFilePath());
    }

    @Override
    public void saveTodoList(ReadOnlyTodoList todoList, Path filePath) throws IOException {
        logger.fine("Attempting to write to TDL data file: " + filePath);
        todoListStorage.saveTodoList(todoList, filePath);
    }


    @Override
    @Subscribe
    public void handleAddressBookChangedEvent(AddressBookChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to AB file"));
        try {
            saveAddressBook(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

    @Override
    @Subscribe
    public void handleTodoListChangedEvent(TodoListChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event, "Local data changed, saving to TDL file"));
        try {
            saveTodoList(event.data);
        } catch (IOException e) {
            raise(new DataSavingExceptionEvent(e));
        }
    }

}
