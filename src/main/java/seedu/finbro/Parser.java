package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;
import seedu.finbro.exception.FinbroException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_VIEW = "view";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SET_LIMIT = "limit";
    private static final String COMMAND_EDIT = "edit";

    public static void parse(String input, ExpenseList expenses, Ui ui) throws FinbroException {
        input = input.trim();

        if (input == null) {
            throw new FinbroException("Invalid command.");
        }

        if (input.equals(COMMAND_ADD)) {
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }

        if (input.startsWith(COMMAND_ADD + " ")) {
            handleAdd(input, expenses, ui);
            return;
        }

        if (input.startsWith(COMMAND_VIEW)) {
            handleView(input, expenses, ui);
            return;
        }

        if (input.startsWith(COMMAND_DELETE)) {
            handleDelete(input, expenses, ui);
            return;
        }

        if (input.startsWith(COMMAND_SET_LIMIT)) {
            handleSetLimit(input, ui);
            return;
        }
        if (input.equals(COMMAND_EDIT + " " + COMMAND_SET_LIMIT)) {
            handleEditLimit(ui);
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
            String dateInput = parts[3];
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
            LocalDate parsedDate;

            try {
                parsedDate = LocalDate.parse(dateInput, inputFormatter);
            } catch (DateTimeParseException e) {
                throw new FinbroException("Invalid date format! Use dd/MM/yyyy");
            }

            String formattedDate = parsedDate.format(outputFormatter);
            Expense expense = new Expense(amount, category, formattedDate);
            expenses.add(expense);
            ui.showExpenseAdded(expense, expenses.size());
        } catch (NumberFormatException e) {
            throw new FinbroException("Amount must be a number.");
        }
    }

    private static void handleView(String input, ExpenseList expenses, Ui ui) throws FinbroException {
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

    private static void handleDelete(String input, ExpenseList expenses, Ui ui) throws FinbroException {
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

    private static void handleSetLimit(String input, Ui ui) throws FinbroException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            ui.showLimit();
            return;
        }

        int limit = 0;
        // check if limit is of valid type
        try {
            limit = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new FinbroException("Monthly spending limit must be a number");
        }

        // check if limit is a valid value
        if (limit < 0) {
            throw new FinbroException("Monthly spending limit must be at least $0");
        }

        Limit.setLimit(limit, ui);
        ui.showLimit();
    }

    private static void handleEditLimit(Ui ui) throws FinbroException {
        double currentLimit = Limit.getLimit();

        ui.showLimitEditMenu(currentLimit);
        String choice = ui.readCommand().trim();

        double newLimit;

        switch (choice) {
        case "1":
            ui.showEnterAmountPrompt("increase");
            newLimit = currentLimit + parsePositiveAmount(ui.readCommand().trim());
            break;
        case "2":
            ui.showEnterAmountPrompt("decrease");
            newLimit = currentLimit - parsePositiveAmount(ui.readCommand().trim());
            if (newLimit < 0) {
                throw new FinbroException("Monthly spending limit must be at least $0");
            }
            break;
        case "3":
            ui.showEnterAmountPrompt("replace");
            newLimit = parsePositiveAmount(ui.readCommand().trim());
            break;
        default:
            throw new FinbroException("Please enter 1, 2, or 3.");
        }

        Limit.setLimit(newLimit, ui);
        ui.showLimit();
    }

    private static double parsePositiveAmount(String input) throws FinbroException {
        double amount;
        try {
            amount = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            throw new FinbroException("Monthly spending limit must be a number");
        }

        if (amount < 0) {
            throw new FinbroException("Monthly spending limit must be at least $0");
        }

        return amount;
    }
}
