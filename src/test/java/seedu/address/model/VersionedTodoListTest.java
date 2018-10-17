package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.testutil.TypicalTasks.ASSIGNMENT;
import static seedu.address.testutil.TypicalTasks.LAB;
import static seedu.address.testutil.TypicalTasks.PROJECT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import seedu.address.testutil.TodoListBuilder;

public class VersionedTodoListTest {

    private final ReadOnlyTodoList todoListWithAssignment = new TodoListBuilder().withTask(ASSIGNMENT).build();
    private final ReadOnlyTodoList todoListWithLab = new TodoListBuilder().withTask(LAB).build();
    private final ReadOnlyTodoList todoListWithProject = new TodoListBuilder().withTask(PROJECT).build();
    private final ReadOnlyTodoList emptyTodoList = new TodoListBuilder().build();

    @Test
    public void commit_singleTodoList_noStatesRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoListList(emptyTodoList);

        versionedTodoList.commit();
        assertTodoListListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                emptyTodoList,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTodoListPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);

        versionedTodoList.commit();
        assertTodoListListStatus(versionedTodoList,
                Arrays.asList(emptyTodoList, todoListWithAssignment, todoListWithLab),
                todoListWithLab,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleTodoListPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        versionedTodoList.commit();
        assertTodoListListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                emptyTodoList,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtEndOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);

        assertTrue(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtStartOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        assertTrue(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_singleTodoList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoListList(emptyTodoList);

        assertFalse(versionedTodoList.canUndo());
    }

    @Test
    public void canUndo_multipleTodoListPointerAtStartOfStateList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertFalse(versionedTodoList.canUndo());
    }

    @Test
    public void canRedo_multipleTodoListPointerNotAtEndOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        assertTrue(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_multipleTodoListPointerAtStartOfStateList_returnsTrue() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertTrue(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_singleTodoList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoListList(emptyTodoList);

        assertFalse(versionedTodoList.canRedo());
    }

    @Test
    public void canRedo_multipleTodoListPointerAtEndOfStateList_returnsFalse() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);

        assertFalse(versionedTodoList.canRedo());
    }

    @Test
    public void undo_multipleTodoListPointerAtEndOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);

        versionedTodoList.undo();
        assertTodoListListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                todoListWithAssignment,
                Collections.singletonList(todoListWithLab));
    }

    @Test
    public void undo_multipleTodoListPointerNotAtStartOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        versionedTodoList.undo();
        assertTodoListListStatus(versionedTodoList,
                Collections.emptyList(),
                emptyTodoList,
                Arrays.asList(todoListWithAssignment, todoListWithLab));
    }

    @Test
    public void undo_singleTodoList_throwsNoUndoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoListList(emptyTodoList);

        assertThrows(VersionedTodoList.NoUndoableStateException.class, versionedTodoList::undo);
    }

    @Test
    public void undo_multipleTodoListPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        assertThrows(VersionedTodoList.NoUndoableStateException.class, versionedTodoList::undo);
    }

    @Test
    public void redo_multipleTodoListPointerNotAtEndOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);

        versionedTodoList.redo();
        assertTodoListListStatus(versionedTodoList,
                Arrays.asList(emptyTodoList, todoListWithAssignment),
                todoListWithLab,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleTodoListPointerAtStartOfStateList_success() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 2);

        versionedTodoList.redo();
        assertTodoListListStatus(versionedTodoList,
                Collections.singletonList(emptyTodoList),
                todoListWithAssignment,
                Collections.singletonList(todoListWithLab));
    }

    @Test
    public void redo_singleTodoList_throwsNoRedoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoListList(emptyTodoList);

        assertThrows(VersionedTodoList.NoRedoableStateException.class, versionedTodoList::redo);
    }

    @Test
    public void redo_multipleTodoListPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedTodoList versionedTodoList = prepareTodoListList(
                emptyTodoList, todoListWithAssignment, todoListWithLab);

        assertThrows(VersionedTodoList.NoRedoableStateException.class, versionedTodoList::redo);
    }

    @Test
    public void equals() {
        VersionedTodoList versionedTodoList = prepareTodoListList(todoListWithAssignment, todoListWithLab);

        // same values -> returns true
        VersionedTodoList copy = prepareTodoListList(todoListWithAssignment, todoListWithLab);
        assertTrue(versionedTodoList.equals(copy));

        // same object -> returns true
        assertTrue(versionedTodoList.equals(versionedTodoList));

        // null -> returns false
        assertFalse(versionedTodoList.equals(null));

        // different types -> returns false
        assertFalse(versionedTodoList.equals(1));

        // different state list -> returns false
        VersionedTodoList differentTodoListList = prepareTodoListList(todoListWithLab, todoListWithProject);
        assertFalse(versionedTodoList.equals(differentTodoListList));

        // different current pointer index -> returns false
        VersionedTodoList differentCurrentStatePointer = prepareTodoListList(
                todoListWithAssignment, todoListWithLab);
        shiftCurrentStatePointerLeftwards(versionedTodoList, 1);
        assertFalse(versionedTodoList.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedTodoList} is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedTodoList#currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedTodoList#currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertTodoListListStatus(VersionedTodoList versionedTodoList,
                                             List<ReadOnlyTodoList> expectedStatesBeforePointer,
                                             ReadOnlyTodoList expectedCurrentState,
                                             List<ReadOnlyTodoList> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new TodoList(versionedTodoList), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedTodoList.canUndo()) {
            versionedTodoList.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyTodoList expectedTodoList : expectedStatesBeforePointer) {
            assertEquals(expectedTodoList, new TodoList(versionedTodoList));
            versionedTodoList.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyTodoList expectedTodoList : expectedStatesAfterPointer) {
            versionedTodoList.redo();
            assertEquals(expectedTodoList, new TodoList(versionedTodoList));
        }

        // check that there are no more states after pointer
        assertFalse(versionedTodoList.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedTodoList.undo());
    }

    /**
     * Creates and returns a {@code VersionedTodoList} with the {@code todoListStates} added into it, and the
     * {@code VersionedTodoList#currentStatePointer} at the end of list.
     */
    private VersionedTodoList prepareTodoListList(ReadOnlyTodoList... todoListStates) {
        assertFalse(todoListStates.length == 0);

        VersionedTodoList versionedTodoList = new VersionedTodoList(todoListStates[0]);
        for (int i = 1; i < todoListStates.length; i++) {
            versionedTodoList.resetData(todoListStates[i]);
            versionedTodoList.commit();
        }

        return versionedTodoList;
    }

    /**
     * Shifts the {@code versionedTodoList#currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedTodoList versionedTodoList, int count) {
        for (int i = 0; i < count; i++) {
            versionedTodoList.undo();
        }
    }
}
