package seedu.finbro.ui;

import seedu.finbro.commands.Command;
import seedu.finbro.finances.Expense;
import seedu.finbro.finances.Limit;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String LINE = "--------------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);

    //@@author Kushalshah0402
    public void showWelcome() {
        showLine();
        showLogo();
        System.out.println("Hello Finbro here for you!");
        System.out.println("Type help to see all available commands.");
        showLine();
    }

    //@@author Kushalshah0402
    public void showGoodbye() {
        showLine();
        System.out.println("Bye! See you again.");
        showLine();
    }

    //@@author Kushalshah0402
    public String readCommand() {
        if (!scanner.hasNextLine()) {
            return "exit";
        }
        return scanner.nextLine().trim();
    }

    //@@author Kushalshah0402
    public void showError(String message) {
        showLine();
        System.out.println("Error: " + message);
        showLine();
    }
    //@@author zihaoalt
    public void showEnterIndexPrompt() {
        showLine();
        System.out.println("Enter the expense index to delete, type -l to list expenses, "
                + "-back to return to category selection, or -exit to cancel.");
        showLine();
    }
    //@@author zihaoalt
    public void showAllCategoryNames(List<String> categoryNames) {
        showLine();
        for (String categoryName : categoryNames) {
            System.out.println("Category Name : " + categoryName);
        }
        showLine();
    }
    //@@author zihaoalt
    public void showExitDeleteMessage() {
        showLine();
        System.out.println("Successfully exit the delete command.");
        showLine();
    }
    //@@author zihaoalt
    public void showCategoryExpenses(String category, List<Expense> expenses) {
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            String toPrint = "Expense " + (i + 1) + " :" + expense.toString();
            showLine();
            System.out.println(toPrint);
            showLine();
        }
    }
    //@@author Kushalshah0402
    public void showEnterAmountPrompt() {
        showLine();
        System.out.println("What is your expense amount?");
    }
    //@@author WangZX2001
    public void showEnterAmountPrompt(String action) {
        showLine();
        switch (action) {
        case "increase":
            System.out.println("Enter the amount to increase by:");
            break;
        case "decrease":
            System.out.println("Enter the amount to decrease by:");
            break;
        case "replace":
            System.out.println("Enter the new monthly budget limit:");
            break;
        default:
            System.out.println("Enter amount:");
            break;
        }
        showLine();
    }

    //@@author Kushalshah0402
    public void showEnterCategoryPrompt() {
        showLine();
        System.out.println("Enter the category:");
        showLine();
    }
    //@@author zihaoalt
    public void showEnterCategoryOptionPrompt() {
        showLine();
        System.out.println("Enter the category, or type -l to list all categories or -exit to cancel.");
    }
    //@@author Kushalshah0402
    public void showEnterDatePrompt() {
        showLine();
        System.out.println("Enter the date (yyyy-MM-dd or today):");
        showLine();
    }

    //@@author Kushalshah0402
    public void showConfirmExpense(Expense e) {
        showLine();
        System.out.println("You entered:");
        System.out.println("  " + e);
        System.out.println("Confirm? [yes/no]");
    }

    //@@author Kushalshah0402
    public void showCancelAddMessage() {
        showLine();
        System.out.println("Expense not added.");
        showLine();
    }
    //@@author zihaoalt
    public void showCancelDeleteMessage() {
        showLine();
        System.out.println("Expense not deleted.");
        showLine();
    }
    //@@author Kushalshah0402
    public void showInlineError(String message) {
        System.out.println("Warning: " + message);
    }

    //@@author zihaoalt
    public void showDeleteError(String message) {
        showLine();
        System.out.println("Error: " + message);
        showLine();
    }

    //@@author Kushalshah0402
    public void showExpenseAdded(Expense e, int total) {
        showLine();
        System.out.println("Got it. I've added this expense:");
        System.out.println("  " + e);
        System.out.println("Now you have " + total + " expenses.");
        showLine();
    }

    //@@author zihaoalt
    public void showExpenseRemoved(Expense e, int total) {
        showLine();
        System.out.println("Got it. I've removed this expense:");
        System.out.println("  " + e);
        System.out.println("Now you have " + total + " expenses.");
        showLine();
    }

    //@@author Kushalshah0402
    public void showAllExpenses(List<Expense> expenses) {
        showLine();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            double total = 0;
            System.out.println("Here are your expenses:");
            System.out.println();
            for (int i = 0; i < expenses.size(); i++) {
                total += expenses.get(i).amount();
                System.out.println((i + 1) + ". " + expenses.get(i));
                System.out.println();
            }
            System.out.printf("Total expenditure: $%.2f\n", total);
        }
        showLine();
    }

    //@@author natmloclam
    public void showLimit() {
        showLine();
        System.out.println("Monthly budget limit: " + String.format("$%.2f", Limit.getLimit()));
        showLine();
    }

    //@@author natmloclam
    public void showChangeLimitWarning(double limit) {
        showLine();
        System.out.println("Are you sure you want to change your monthly budget limit to "
                + String.format("$%.2f", limit) + "? [yes/no]");
        showLine();
    }

    //@@author natmloclam
    public void showCancelChangeLimitMessage() {
        showLine();
        System.out.println("Monthly budget limit was not changed");
    }

    //@@author WangZX2001
    public void showLimitEditMenu(double currentLimit) {
        showLine();
        System.out.println("Current monthly budget limit: " + String.format("$%.2f", currentLimit));
        System.out.println("Choose an action:");
        System.out.println("1. Increase limit");
        System.out.println("2. Decrease limit");
        System.out.println("3. Replace limit");
        showLine();
    }

    //@@author Kushalshah0402
    public void showBudgetReminder(double limit) {
        showLine();
        System.out.println("Warning: You are close to your monthly spending limit of $"
                + String.format("%.2f", limit) + "!");
        showLine();
    }

    //@@author Kushalshah0402
    public void showBudgetExceeded(double limit) {
        showLine();
        System.out.println("Warning: You have exceeded your monthly spending limit of $"
                + String.format("%.2f", limit) + "!");
        showLine();
    }

    //@@author Kushalshah0402
    public void showLine() {
        System.out.println(LINE);
    }

    //@@author zihaoalt
    public void showCommandHelpMessage(Command command) {
        showLine();
        System.out.println(command.getHelpMessage());
        showLine();
    }

    //@@author natmloclam
    public void showHelpMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    //@@author AK47ofCode
    public void showLogo() {
        System.out.println("""
                 ________  __            __
                |        \\|  \\          |  \\
                | $$$$$$$$ \\$$ _______  | $$____    ______    ______
                | $$__    |  \\|       \\ | $$    \\  /      \\  /      \\
                | $$  \\   | $$| $$$$$$$\\| $$$$$$$\\|  $$$$$$\\|  $$$$$$\\
                | $$$$$   | $$| $$  | $$| $$  | $$| $$   \\$$| $$  | $$
                | $$      | $$| $$  | $$| $$__/ $$| $$      | $$__/ $$
                | $$      | $$| $$  | $$| $$    $$| $$       \\$$    $$
                 \\$$       \\$$ \\$$   \\$$ \\$$$$$$$  \\$$        \\$$$$$$
                """);
    }

    public void showVisual(String output) {
        showLine();
        System.out.println("=== Monthly Spendings ===");
        System.out.println(output);
        showLine();
    }
    
    //@@author WangZX2OO1
    public void showEnterSourceCurrencyPrompt() {
        showLine();
        System.out.println("Enter source currency:");
    }

    //@@author WangZX2001
    public void showEnterTargetCurrencyPrompt() {
        System.out.println("Enter target currency:");
    }

    //@@author WangZX2001
    public void showChooseExpenseEntryPrompt() {
        System.out.println("Which expense entry would you like to convert?");
        showLine();
    }

    //@@author WangZX2001
    public void showCurrencyConversionResult(int index, double originalAmount,
                                             String fromCurrency, String toCurrency,
                                             double convertedAmount) {
        showLine();
        System.out.println("Expense #" + index);
        System.out.printf("%.2f %s = %.2f %s%n",
                originalAmount, fromCurrency, convertedAmount, toCurrency);
        showLine();
    }

    public void showNoConversionNeeded(String currency) {
        showLine();
        System.out.println("Source and target currencies are both " + currency + ". No conversion needed.");
        showLine();
    }
}
