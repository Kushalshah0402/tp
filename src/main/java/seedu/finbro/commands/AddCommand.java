package seedu.finbro.commands;

import seedu.finbro.finances.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.Expense;
import seedu.finbro.utils.NaturalDateParser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCommand extends Command {
    private static final Logger logger = Logger.getLogger(AddCommand.class.getName());
    private static final double HIGH_VALUE_THRESHOLD = 10000;
    private final String arg;
    
    //@@author natmloclam
    public AddCommand(String arg) {
        assert arg != null : "Argument string should not be null";
        this.arg = arg;
    }

    //@@author Kushalshah0402 WangZX2001
    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        assert expenses != null : "ExpenseList should not be null";
        assert ui != null : "Ui should not be null";

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
        category = category.toLowerCase();
        Expense expense = new Expense(amount, category, formattedDate);
        assert expense != null : "Expense should not be null";

        ui.showConfirmExpense(expense);
        String confirm = ui.readCommand().trim();

        if (!confirm.equalsIgnoreCase("yes")) {
            logger.log(Level.INFO, "Expense addition cancelled by user");
            ui.showCancelAddMessage();
            return;
        }

        // HIGH VALUE CHECK (NEW)
        if (amount > HIGH_VALUE_THRESHOLD) {
            logger.log(Level.WARNING, "High value expense detected: " + amount);
            System.out.println(
                    "Since the expense amount is huge, we would like to double confirm an expense for $"
                    + String.format("%.2f", amount)
            );
            System.out.println("Do you still want to proceed? [yes/no]");
            String finalConfirm = ui.readCommand().trim();
            if (!finalConfirm.equalsIgnoreCase("yes")) {
                logger.log(Level.INFO, "High value expense rejected at second confirmation");
                ui.showCancelAddMessage();
                return;
            }
        }

        // ADD EXPENSE
        expenses.add(expense);
        logger.log(Level.INFO, "Expense confirmed and added: " + expense);
        ui.showExpenseAdded(expense, expenses.size());
    }

    //@@author Kushalshah0402
    private void runWalkthrough(ExpenseList expenses, Ui ui) {
        assert expenses != null;
        assert ui != null;
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
                    logger.log(Level.WARNING, "Invalid amount entered: non-positive");
                    ui.showInlineError("Amount must be a positive number.");
                    continue;
                }
                logger.log(Level.INFO, "Valid amount entered: " + amount);
                break;
            } catch (NumberFormatException e) {
                logger.log(Level.WARNING, "Invalid amount entered: not a number");
                ui.showInlineError("Please enter numbers only.");
            }
        }

        // CATEGORY LOOP
        while (true) {
            ui.showEnterCategoryPrompt();
            category = ui.readCommand();

            if (category.isBlank()) {
                logger.log(Level.WARNING, "Empty category entered");
                ui.showInlineError("Category cannot be empty.");
                continue;
            }

            // Disallow numbers
            if (!category.matches("[a-zA-Z]+")) {
                logger.log(Level.WARNING, "Invalid category entered: " + category);
                ui.showInlineError("Category must contain letters only.");
                continue;
            }
            logger.log(Level.INFO, "Valid category entered: " + category);
            break;
        }

        // DATE LOOP
        while (true) {
            ui.showEnterDatePrompt();
            String dateInput = ui.readCommand();
            try {
                LocalDate parsedDate = NaturalDateParser.parse(dateInput);
                validateDateRange(parsedDate);
                formattedDate = parsedDate.format(
                        DateTimeFormatter.ofPattern("d MMMM yyyy")
                );
                logger.log(Level.INFO, "Valid date entered: " + formattedDate);
                break;
            } catch (FinbroException e) {
                logger.log(Level.WARNING, "Invalid date entered: " + dateInput);
                ui.showInlineError(e.getMessage());
            }
        }

        // CONFIRMATION
        category = category.toLowerCase();
        Expense expense = new Expense(amount, category, formattedDate);
        assert expense != null : "Expense should not be null";
        ui.showConfirmExpense(expense);
        String confirm = ui.readCommand().trim();
        if (!confirm.equalsIgnoreCase("yes")) {
            logger.log(Level.INFO, "Expense addition cancelled by user");
            ui.showCancelAddMessage();
            return;
        }

        // HIGH VALUE CHECK
        if (amount > HIGH_VALUE_THRESHOLD) {
            logger.log(Level.WARNING, "High value expense detected: " + amount);
            System.out.println(
                    "Since the expense amount is huge, we would like to double confirm an expense for $"
                    + String.format("%.2f", amount)
            );
            System.out.println("Do you still want to proceed? [yes/no]");
            String finalConfirm = ui.readCommand().trim();
            if (!finalConfirm.equalsIgnoreCase("yes")) {
                logger.log(Level.INFO, "High value expense rejected at second confirmation");
                ui.showCancelAddMessage();
                return;
            }
        }
        expenses.add(expense);
        logger.log(Level.INFO, "Expense confirmed and added: " + expense);
        ui.showExpenseAdded(expense, expenses.size());
    }

    //@@author Kushalshah0402 WangZX2001
    private void verifyInputLength(String input) throws FinbroException {
        logger.log(Level.INFO, "Verifying input length for: " + input);
        String[] parts = input.trim().split(" ");
        logger.log(Level.FINE, "Split input into " + parts.length + " parts");

        if (parts.length < 3) {
            logger.log(Level.WARNING, "Invalid input length: expected >= 3 but got " + parts.length);
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }
        logger.log(Level.INFO, "Input length verification passed");
    }

    //@@author Kushalshah0402
    private double verifyAmount(String input) throws FinbroException {
        String[] parts = input.split(" ");
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
    public static String verifyDate(String input) throws FinbroException {
        logger.log(Level.INFO, "Verifying date from input: " + input);
        String[] parts = input.trim().split(" ");

        if (parts.length < 3) {
            logger.log(Level.WARNING, "Date parsing failed: insufficient parts");
            throw new FinbroException("Missing Attributes.");
        }

        // Date is everything after "<amount> <category>".
        String dateInput = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        logger.log(Level.INFO, "Extracted date input: " + dateInput);

        try {
            LocalDate parsedDate = NaturalDateParser.parse(dateInput);
            validateDateRange(parsedDate);
            logger.log(Level.INFO, "Parsed date successfully: " + parsedDate);

            DateTimeFormatter outputFormatter =
                    DateTimeFormatter.ofPattern("d MMMM yyyy");

            String formattedDate = parsedDate.format(outputFormatter);
            logger.log(Level.INFO, "Formatted date: " + formattedDate);

            return formattedDate;

        } catch (FinbroException e) {
            logger.log(Level.WARNING,
                    "Failed to parse date input: " + dateInput + " | Error: " + e.getMessage());
            throw e;
        }
    }

    //@author Kushalshah0402
    private static void validateDateRange(LocalDate date) throws FinbroException {
        int year = date.getYear();
        int currentYear = LocalDate.now().getYear();

        if (year < 2000 || year > currentYear) {
            logger.log(Level.WARNING, "Date out of allowed range: " + date);
            throw new FinbroException("Date must be between year 2000 and current year.");
        }

        if (date.isAfter(LocalDate.now())) {
            logger.log(Level.WARNING, "Future date entered: " + date);
            throw new FinbroException("Date cannot be in the future.");
        }
    }

    //@@author Kushalshah0402
    @Override
    public String getHelpMessage() {
        return """
                Adds a new expense entry.
                Format: add <amount> <category> <date> or 'add' for us to walk you through the process step-by-step.
                Use: Records an expense under the given category on the given date.
                Note: amount must be positive and date must be in yyyy-mm-dd format.""";
    }
}
