package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.Expense;

public class DeleteCommand extends Command {
    @Override
    public void execute(String input, ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        String[] parts = input.split(" ");

        if (parts.length < 3) {
            throw new FinbroException("Usage: delete <category> #<number>");
        }

        try {
            int number = Integer.parseInt(parts[2]);
            String category = parts[1];

            Expense expense = expenses.removeByCategoryIndex(category, number);
            ui.showExpenseRemoved(expense, expenses.size());
        } catch (NumberFormatException e) {
            throw new FinbroException("Expense number must be a number.");
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Deletes a specific expense from a category.
                Format: delete <category> <number>
                Use: Permanently removes the numbered expense in that category.
                Note: use the displayed index number, such as 2.""";
    }
}
