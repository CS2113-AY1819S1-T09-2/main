package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.task.Task;

/**
 * Adds a task to the to-do list.
 */
public class AddTaskCommand extends Command {

    public static final String COMMAND_WORD = "TDL_add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a task to the to-do list. "
            + "Parameters: "
            + PREFIX_TASK + "TASK"
            + PREFIX_MODULE + "MODULE "
            + "[" + PREFIX_DATE + "DATE]..."
            + "[" + PREFIX_PRIORITY + "PRIORITY]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_TASK + "Tutorial 5 "
            + PREFIX_MODULE + "CS2101 "
            + PREFIX_DATE + "12-09 "
            + PREFIX_PRIORITY + "1 ";

    public static final String MESSAGE_SUCCESS = "New task added: %1$s";
    public static final String MESSAGE_DUPLICATE_TASK = "This person already exists in the to-do list";

    private final Task toAdd;

    /**
     * Creates an TDLAddCommand to add the specified {@code Task}
     */
    public AddTaskCommand(Task task) {
        requireNonNull(task);
        toAdd = task;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.hasTask(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TASK);
        }

        model.addTask(toAdd);
        model.commitTodoList();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddTaskCommand // instanceof handles nulls
                && toAdd.equals(((AddTaskCommand) other).toAdd));
    }
}
