package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.exception.FinbroException;

public class Parser {
    private static final String COMMAND_ADD = "add ";
    private static final String COMMAND_VIEW_ALL = "view all";

    public static void parse(String input, ExpenseList expenses, Ui ui) throws FinbroException {
        input = input.trim();
        if (input.equals(COMMAND_VIEW_ALL)) {
            ui.showAllExpenses(expenses.getAll());
            return;
        }

        if (input.startsWith(COMMAND_ADD)) {
            handleAdd(input, expenses, ui);
            return;
        }
        throw new FinbroException("Invalid command.");
    }

    private static void handleAdd(String input, ExpenseList expenses, Ui ui) throws FinbroException {
        String[] parts = input.split(" ");
        if (parts.length < 4) {
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }

        try {
            double amount = Double.parseDouble(parts[1]);
            String category = parts[2];
            String date = parts[3];

            Expense expense = new Expense(amount, category, date);
            expenses.add(expense);
            ui.showExpenseAdded(expense, expenses.size());
        } catch (NumberFormatException e) {
            throw new FinbroException("Amount must be a number.");
        }
    }
}