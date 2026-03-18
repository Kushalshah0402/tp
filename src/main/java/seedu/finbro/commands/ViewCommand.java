package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

public class ViewCommand extends Command {
    private static final String COMMAND_VIEW = "view";

    @Override
    public void execute(String input, ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        if (input.equals(COMMAND_VIEW)) {
            throw new FinbroException("Usage: view <category> OR view all");
        } else if (input.equals(COMMAND_VIEW + " " + "all")) {
            ui.showAllExpenses(expenses.getAll());
        } else if (input.startsWith(COMMAND_VIEW + " ")) {
            String category = input.substring((COMMAND_VIEW + " ").length());
            if (expenses.getCategoryExpenses(category).isEmpty()) {
                throw new FinbroException("Current View Category only supports exact matches, or empty category.");
            }
            ui.showAllExpenses(expenses.getCategoryExpenses(category));
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
