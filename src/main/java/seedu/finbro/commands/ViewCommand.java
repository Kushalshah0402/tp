package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

public class ViewCommand extends Command {
    private static final String COMMAND_VIEW = "view";
    private static final String EMPTY = "";
    private static final String ALL = "all";

    private final String arg;

    public ViewCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        switch (arg) {
        case EMPTY:
            throw new FinbroException("Usage: view <category> OR view all");
        case ALL:
            ui.showAllExpenses(expenses.getAll());
            break;
        default:
            if (expenses.getCategoryExpenses(arg).isEmpty()) {
                throw new FinbroException("Current View Category only supports exact matches, or empty category.");
            }
            ui.showAllExpenses(expenses.getCategoryExpenses(arg));
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Shows all recorded expenses.
                Format: view all
                Use: Displays every expense grouped by category.
                
                Shows expenses in a specific category.
                Format: view <category>
                Use: Displays only the expenses under the given category.
                Note: category names are case-insensitive.""";
    }
}
