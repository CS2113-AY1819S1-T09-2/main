package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskDate;
import seedu.address.model.task.TaskModule;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.TaskPriority;

/**
 * Edits the details of an existing task in the address book.
 */
public class EditTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the task identified "
            + "by the index number used in the displayed task list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_TASK + "TASK] "
            + "[" + PREFIX_MODULE + "MODULE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_PRIORITY + "PRIORITY] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_TASK + "Tutorial 2 "
            + PREFIX_MODULE + "CS2113";

    public static final String MESSAGE_EDIT_TASK_SUCCESS = "Edited Task: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_TASK = "This task already exists in the to-do list.";

    private final Index index;
    private final EditTaskDescriptor tdleditTaskDescriptor;

    /**
     * @param index of the task in the filtered task list to edit
     * @param tdleditTaskDescriptor details to edit the task with
     */
    public EditTaskCommand(Index index, EditTaskDescriptor tdleditTaskDescriptor) {
        requireNonNull(index);
        requireNonNull(tdleditTaskDescriptor);

        this.index = index;
        this.tdleditTaskDescriptor = new EditTaskDescriptor(tdleditTaskDescriptor);
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        List<Task> lastShownList = model.getFilteredTaskList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
        }

        Task taskToEdit = lastShownList.get(index.getZeroBased());
        Task tdleditedTask = createEditedTask(taskToEdit, tdleditTaskDescriptor);

        if (!taskToEdit.isSameTask(tdleditedTask) && model.hasTask(tdleditedTask)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.updateTask(taskToEdit, tdleditedTask);
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        model.commitTodoList();
        return new CommandResult(String.format(MESSAGE_EDIT_TASK_SUCCESS, tdleditedTask));
    }

    /**
     * Creates and returns a {@code Task} with the details of {@code taskToEdit}
     * edited with {@code tdleditTaskDescriptor}.
     */
    private static Task createEditedTask(Task taskToEdit, EditTaskDescriptor tdleditTaskDescriptor) {
        assert taskToEdit != null;

        TaskName updatedName = tdleditTaskDescriptor.getName().orElse(taskToEdit.getName());
        TaskModule updatedModule = tdleditTaskDescriptor.getModule().orElse(taskToEdit.getModule());
        TaskDate updatedDate = tdleditTaskDescriptor.getDate().orElse(taskToEdit.getDate());
        TaskPriority updatedPriority = tdleditTaskDescriptor.getPriority().orElse(taskToEdit.getPriority());

        return new Task(updatedName, updatedModule, updatedDate, updatedPriority);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditTaskCommand)) {
            return false;
        }

        // state check
        EditTaskCommand e = (EditTaskCommand) other;
        return index.equals(e.index)
                && tdleditTaskDescriptor.equals(e.tdleditTaskDescriptor);
    }

    /**
     * Stores the details to edit the task with. Each non-empty field value will replace the
     * corresponding field value of the task.
     */
    public static class EditTaskDescriptor {
        private TaskDate taskDate;
        private TaskModule taskModule;
        private TaskName taskName;
        private TaskPriority taskPriority;

        public EditTaskDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditTaskDescriptor(EditTaskDescriptor toCopy) {
            setName(toCopy.taskName);
            setModule(toCopy.taskModule);
            setDate(toCopy.taskDate);
            setPriority(toCopy.taskPriority);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(taskName, taskModule, taskDate, taskPriority);
        }

        public void setName(TaskName name) {
            this.taskName = name;
        }

        public Optional<TaskName> getName() {
            return Optional.ofNullable(taskName);
        }

        public void setModule(TaskModule module) {
            this.taskModule = module;
        }

        public Optional<TaskModule> getModule() {
            return Optional.ofNullable(taskModule);
        }

        public void setDate(TaskDate date) {
            this.taskDate = date;
        }

        public Optional<TaskPriority> getPriority() {
            return Optional.ofNullable(taskPriority);
        }

        public void setPriority(TaskPriority taskPriority) {
            this.taskPriority = taskPriority;
        }

        public Optional<TaskDate> getDate() {
            return Optional.ofNullable(taskDate);
        }



        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTaskDescriptor)) {
                return false;
            }

            // state check
            EditTaskDescriptor e = (EditTaskDescriptor) other;

            return getName().equals(e.getName())
                    && getName().equals(e.getName())
                    && getModule().equals(e.getModule())
                    && getDate().equals(e.getDate())
                    && getPriority().equals(e.getPriority());
        }
    }
}
