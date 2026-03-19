package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.Expense;

public class DeleteCommand extends Command {
    private final String arg;

    public DeleteCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        verifyInputLength(arg);
        String category = filterCategory(arg);
        int index = verifyIndex(arg);

        Expense expense = expenses.removeByCategoryIndex(category, index);
        ui.showExpenseRemoved(expense, expenses.size());
    }


    private void verifyInputLength(String input) throws FinbroException {
        String [] parts = input.split(" ");
        if (parts.length < 2) {
            throw new FinbroException("Usage: delete <category> #<number>");
        }
    }

    private int verifyIndex(String input) throws FinbroException {
        String[] parts = input.split(" ");

        int index;
        try {
            index = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new FinbroException("Expense number must be a number.");
        }
        return index;
    }

    private String filterCategory(String input) throws FinbroException {
        String[] parts = input.split(" ");
        return parts[0];
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
