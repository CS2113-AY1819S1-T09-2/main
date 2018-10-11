package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONEY;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.expenditureinfo.Expenditure;

/**
 * Adds an expenditure to the expenditure tracker.
 */
public class AddExpenditureCommand extends Command {

    public static final String COMMAND_WORD = "ET add";
    public static final String COMMAND_ALIAS = "a";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds an expenditure to the expenditure tracker. "
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_MONEY + "MONEY "
            + PREFIX_CATEGORY + "CATEGORY "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "25/12/2017 "
            + PREFIX_MONEY + "1000 "
            + PREFIX_CATEGORY + "Food ";

    public static final String MESSAGE_SUCCESS = "New expenditure added: %1$s";

    private final Expenditure toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddExpenditureCommand(Expenditure expenditure) {
        requireNonNull(expenditure);
        toAdd = expenditure;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);


        model.addExpenditure(toAdd);
        model.commitAddressBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    /*
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
    */
}
