package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.Expense;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());
    private final String arg;

    //@@author natmloclam
    public AddCommand(String arg) {
        this.arg = arg;
    }

    //@@author Kushalshah0402
    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {

        //Walkthrough mode
        if (arg.isBlank()) {
            runWalkthrough(expenses, ui);
            return;
        }

        //Strict mode (existing behavior)
        verifyInputLength(arg);
        double amount = verifyAmount(arg);
        String category = filterCategory(arg);
        String formattedDate = verifyDate(arg);
        logger.log(Level.INFO,
                "Attempting to add expense amount {0}, category {1}, date {2}",
                new Object[]{amount, category, formattedDate});

        Expense expense = new Expense(amount, category, formattedDate);
        expenses.add(expense);
        logger.log(Level.INFO,
                "Successfully added expense in category " + category + " $" + amount);
        ui.showExpenseAdded(expense, expenses.size());
    }

    //@@author Kushalshah0402
    private void runWalkthrough(ExpenseList expenses, Ui ui) {
        double amount;
        String category;
        String formattedDate;

        // AMOUNT LOOP
        while (true) {
            ui.showEnterAmountPrompt();
            String input = ui.readCommand();
            try {
                amount = Double.parseDouble(input);
                if (amount <= 0) {
                    ui.showInlineError("Amount must be a positive number.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ui.showInlineError("Please enter numbers only.");
            }
        }

        // CATEGORY LOOP
        while (true) {
            ui.showEnterCategoryPrompt();
            category = ui.readCommand();

            if (category.isBlank()) {
                ui.showInlineError("Category cannot be empty.");
                continue;
            }

            // Disallow numbers
            if (!category.matches("[a-zA-Z]+")) {
                ui.showInlineError("Category must contain letters only.");
                continue;
            }
            break;
        }

        // DATE LOOP
        while (true) {
            ui.showEnterDatePrompt();
            String dateInput = ui.readCommand();
            try {
                formattedDate = verifyDate("0 0 " + dateInput);
                break;
            } catch (FinbroException e) {
                ui.showInlineError(e.getMessage());
            }
        }

        // CONFIRMATION
        Expense expense = new Expense(amount, category, formattedDate);
        ui.showConfirmExpense(expense);
        String confirm = ui.readCommand();
        if (confirm.equalsIgnoreCase("yes")) {
            expenses.add(expense);
            ui.showExpenseAdded(expense, expenses.size());
        } else {
            ui.showCancelAddMessage();
        }
    }

    //@@author Kushalshah0402
    private void verifyInputLength(String input) throws FinbroException {
        String [] parts = input.split(" ");
        if (parts.length != 3) {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }
    }

    //@@author Kushalshah0402
    private double verifyAmount(String input) throws FinbroException {
        String[] parts =  input.split(" ");
        double amount = 0;
        try {
            amount = Double.parseDouble(parts[0]);
            if (amount < 0) {
                logger.log(Level.WARNING, "Invalid amount (amount is negative)");
                throw new FinbroException("Amount must be a positive number.");
            }
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid number in expense amount");
            throw new FinbroException("Amount must be a number.");
        }
        return amount;
    }
    //@@author Kushalshah0402
    private String filterCategory(String input) {
        String[] parts = input.split(" ");
        return parts[1];
    }
    //@@author Kushalshah0402
    private String verifyDate(String input) throws FinbroException {
        String[] parts = input.split(" ");
        String inputDate = parts[2];

        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
        LocalDate parsedDate;

        try {
            parsedDate = LocalDate.parse(inputDate, inputFormatter);
        } catch (DateTimeParseException e) {
            logger.log(Level.WARNING, "Invalid date format");
            throw new FinbroException("Invalid date format! Use yyyy-MM-dd");
        }

        return parsedDate.format(outputFormatter);
    }
    //@@author Kushalshah0402
    @Override
    public String getHelpMessage() {
        return """
                Adds a new expense entry.
                Format: add <amount> <category> <date>
                Use: Records an expense under the given category on the given date.
                Note: amount must be positive and date must be in yyyy-mm-dd format.""";
    }
}
