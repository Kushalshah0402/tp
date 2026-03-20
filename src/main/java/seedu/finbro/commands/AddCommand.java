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

    public AddCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        verifyInputLength(arg);
        double amount = verifyAmount(arg);
        String category = filterCategory(arg);
        String formattedDate = verifyDate(arg);

        logger.log(Level.INFO,
                "Attempting to add expense amount {0}, category {1}, date {2}",
                new Object[]{amount, category, formattedDate});

        Expense expense = new Expense(amount, category, formattedDate);
        expenses.add(expense);
        logger.log(Level.INFO, "Successfully added expense in category " + category + " $" + amount);
        ui.showExpenseAdded(expense, expenses.size());
    }


    private void verifyInputLength(String input) throws FinbroException {
        String [] parts = input.split(" ");
        if (parts.length != 3) {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }
    }

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

    private String filterCategory(String input) {
        String[] parts = input.split(" ");
        return parts[1];
    }

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

    @Override
    public String getHelpMessage() {
        return """
                Adds a new expense entry.
                Format: add <amount> <category> <date>
                Use: Records an expense under the given category on the given date.
                Note: amount must be positive and date must be in yyyy-mm-dd format.""";
    }
}
