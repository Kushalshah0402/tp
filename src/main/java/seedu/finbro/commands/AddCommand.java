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
        ParsedAddInput parsed = parseStrictInput(arg);
        confirmAndAdd(expenses, ui, parsed.amount(), parsed.category(), parsed.formattedDate());
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

            // Disallow numeric-only categories (e.g. "123"). Multi-word categories are allowed.
            if (isNumericOnly(category)) {
                logger.log(Level.WARNING, "Invalid category entered (numeric-only): " + category);
                ui.showInlineError("Category cannot be a number.");
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

        confirmAndAdd(expenses, ui, amount, category, formattedDate);
    }

    private void confirmAndAdd(ExpenseList expenses, Ui ui, double amount, String category, String formattedDate) {
        logger.log(Level.INFO,
                "Attempting to add expense amount {0}, category {1}, date {2}",
                new Object[]{amount, category, formattedDate});

        String normalizedCategory = category.toLowerCase();
        Expense expense = new Expense(amount, normalizedCategory, formattedDate);
        assert expense != null : "Expense should not be null";

        if (!confirmExpense(ui, expense)) {
            logger.log(Level.INFO, "Expense addition cancelled by user");
            ui.showCancelAddMessage();
            return;
        }

        if (amount > HIGH_VALUE_THRESHOLD) {
            logger.log(Level.WARNING, "High value expense detected: " + amount);
            if (!confirmHighValue(ui, amount)) {
                logger.log(Level.INFO, "High value expense rejected at second confirmation");
                ui.showCancelAddMessage();
                return;
            }
        }

        expenses.add(expense);
        logger.log(Level.INFO, "Expense confirmed and added: " + expense);
        ui.showExpenseAdded(expense, expenses.size());
    }

    private static boolean confirmExpense(Ui ui, Expense expense) {
        ui.showConfirmExpense(expense);
        String confirm = ui.readCommand().trim();
        return confirm.equalsIgnoreCase("yes");
    }

    private static boolean confirmHighValue(Ui ui, double amount) {
        System.out.println(
                "Since the expense amount is huge, we would like to double confirm an expense for $"
                        + String.format("%.2f", amount)
        );
        System.out.println("Do you still want to proceed? [yes/no]");
        String finalConfirm = ui.readCommand().trim();
        return finalConfirm.equalsIgnoreCase("yes");
    }

    private record ParsedAddInput(double amount, String category, String formattedDate) { }

    private static ParsedAddInput parseStrictInput(String input) throws FinbroException {
        logger.log(Level.INFO, "Parsing strict add input: " + input);
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 3) {
            throw new FinbroException("Usage: add <amount> <category> <date>");
        }

        double amount = parseAmountToken(parts[0]);
        ParsedDate parsedDate = parseDateFromSuffix(parts);

        String category = String.join(" ", Arrays.copyOfRange(parts, 1, parsedDate.dateStartIndex()));
        verifyCategory(category);

        return new ParsedAddInput(amount, category, parsedDate.formattedDate());
    }

    private record ParsedDate(int dateStartIndex, String formattedDate) { }

    private static ParsedDate parseDateFromSuffix(String[] parts) throws FinbroException {
        // Choose the shortest date suffix that parses, to maximize category tokens.
        for (int i = parts.length - 1; i >= 2; i--) {
            String candidate = String.join(" ", Arrays.copyOfRange(parts, i, parts.length));
            try {
                LocalDate parsed = NaturalDateParser.parse(candidate);
                validateDateRange(parsed);
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy");
                return new ParsedDate(i, parsed.format(outputFormatter));
            } catch (FinbroException ignored) {
                // try a longer suffix
            }
        }

        // Let NaturalDateParser produce the user-facing error message.
        String dateInput = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));
        NaturalDateParser.parse(dateInput); // throws
        throw new FinbroException("Invalid date.");
    }

    private static double parseAmountToken(String token) throws FinbroException {
        try {
            double amount = Double.parseDouble(token);
            if (amount <= 0) {
                throw new FinbroException("Amount must be a positive number.");
            }
            return amount;
        } catch (NumberFormatException e) {
            throw new FinbroException("Amount must be a number.");
        }
    }

    private static void verifyCategory(String category) throws FinbroException {
        if (category == null || category.isBlank()) {
            throw new FinbroException("Category cannot be empty.");
        }
        if (isNumericOnly(category)) {
            throw new FinbroException("Category cannot be a number.");
        }
    }

    private static boolean isNumericOnly(String input) {
        return input != null && input.trim().matches("\\d+");
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
                Note: amount must be positive.
                      Category can be multiple words but cannot be only numbers.
                      Date supports natural language (e.g. today, 2 days ago).""";
    }

    @Override
    public boolean checksBudget() {
        return true;
    }
}
