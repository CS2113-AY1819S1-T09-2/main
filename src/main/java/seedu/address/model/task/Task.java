package seedu.address.model.task;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * Represents a Task in the to-do list.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Task {

    // Identity fields
    private final TaskName name;
    private final TaskModule module;

    // Data fields
    private final TaskDate date;
    private final TaskPriority priority;

    private boolean isComplete;

    /*
     *Compare two tasks based on their deadlines
     */
    public static Comparator<Task>compareDeadlines
            =(Task a, Task b) -> a.getDate().value.compareTo(b.getDate().value);
    /*
     *Compare two tasks based on their module codes
     */
    public static Comparator<Task>compareModule
            =(Task a, Task b) -> a.getModule().value.compareTo(b.getModule().value);
    /*
     *Compare two tasks based on their priority
     */
    public static Comparator<Task>comparePriority
            =(Task a, Task b) -> a.getPriority().value.compareTo(b.getPriority().value);

    /**
     * Every field must be present and not null.
     */
    public Task(TaskName name, TaskModule module, TaskDate date, TaskPriority priority, boolean isComplete) {
        requireAllNonNull(name, module, date, priority);
        this.name = name;
        this.module = module;
        this.date = date;
        this.priority = priority;
        this.isComplete = isComplete;
    }

    public TaskName getName() {
        return name;
    }

    public TaskModule getModule() {
        return module;
    }

    public TaskDate getDate() {
        return date;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public boolean getComplete() {
        return isComplete;
    }

    public void setAsCompleted() {
        isComplete = true;
    }

    public void setAsUncompleted() {
        isComplete = false;
    }

    /**
     * Returns true if both tasks are totally the same.
     * This defines a weaker notion of equality between two tasks.
     */
    public boolean isSameTask(Task otherTask) {
        if (otherTask == this) {
            return true;
        }

        return otherTask != null
                && otherTask.getName().equals(getName())
                && otherTask.getModule().equals(getModule());
    }

    /**
     * Returns true if both tasks have the same identity and data fields.
     * This defines a stronger notion of equality between two tasks.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Task)) {
            return false;
        }

        Task otherTask = (Task) other;
        return otherTask.getName().equals(getName())
                && otherTask.getModule().equals(getModule())
                && otherTask.getDate().equals(getDate())
                && otherTask.getPriority().equals(getPriority());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, module, date, priority, isComplete);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Module: ")
                .append(getModule())
                .append(" Date: ")
                .append(getDate())
                .append(" Priority: ")
                .append(getPriority());
        return builder.toString();
    }

}
