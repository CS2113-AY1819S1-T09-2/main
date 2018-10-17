package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalTasks.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.task.Task;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.testutil.TodoListBuilder;
import seedu.address.testutil.TaskBuilder;

public class TodoListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final TodoList todoList = new TodoList();

    private final TodoList todoListWithLabAndAssignment = new TodoListBuilder().withTask(LAB)
            .withTask(ASSIGNMENT).build();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), todoList.getTaskList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        todoList.resetData(null);
    }

    @Test
    public void resetData_withValidReadOnlyTodoList_replacesData() {
        TodoList newData = getTypicalTodoList();
        todoList.resetData(newData);
        assertEquals(newData, todoList);
    }

    @Test
    public void resetData_withDuplicateTasks_throwsDuplicateTaskException() {
        // Two tasks with the same identity fields
        Task editedEssay = new TaskBuilder(ESSAY).withName("CG2028").withPriority("30-08")
                .build();
        List<Task> newTasks = Arrays.asList(ESSAY, editedEssay);
        TodoListStub newData = new TodoListStub(newTasks);

        thrown.expect(DuplicateTaskException.class);
        todoList.resetData(newData);
    }

    @Test
    public void hasTask_nullTask_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        todoList.hasTask(null);
    }

    @Test
    public void hasTask_taskNotInTodoList_returnsFalse() {
        assertFalse(todoList.hasTask(ESSAY));
    }

    @Test
    public void hasTask_taskInTodoList_returnsTrue() {
        todoList.addTask(ESSAY);
        assertTrue(todoList.hasTask(ESSAY));
    }

    @Test
    public void hasTask_taskWithSameIdentityFieldsInTodoList_returnsTrue() {
        todoList.addTask(ESSAY);
        Task editedAlice = new TaskBuilder(ESSAY).withName("CG2028").withPriority("30-08")
                .build();
        assertTrue(todoList.hasTask(editedAlice));
    }

    @Test
    public void getTaskList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        todoList.getTaskList().remove(0);
    }
/*
    @Test
    public void removeTag_nonExistentTag_todoListUnchanged() throws Exception {
        todoListWithBobAndAmy.removeTag(new Tag(VALID_TAG_UNUSED));
        TodoList expectedTodoList = new TodoListBuilder().withTask(LAB).withTask(ASSIGNMENT).build();
        assertEquals(expectedTodoList, todoListWithBobAndAmy);
    }
    */
/*
    @Test
    public void removeTag_tagUsedByMultipleTasks_tagRemoved() throws Exception {
        todoListWithLabAndAssignment.removeTag(new Tag(VALID_TAG_FRIEND));
        Task amyWithoutFriendTag = new TaskBuilder(ASSIGNMENT).withTags().build();
        Task bobWithoutFriendTag = new TaskBuilder(LAB).withTags(VALID_TAG_HUSBAND).build();
        TodoList expectedTodoList = new TodoListBuilder().withTask(bobWithoutFriendTag)
                .withTask(amyWithoutFriendTag).build();
        assertEquals(expectedTodoList, todoListWithBobAndAmy);
    }
    */

    /**
     * A stub ReadOnlyTodoList whose tasks list can violate interface constraints.
     */
    private static class TodoListStub implements ReadOnlyTodoList {
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        TodoListStub(Collection<Task> tasks) {
            this.tasks.setAll(tasks);
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }
    }

}
