package seedu.finbro.ui;

import seedu.finbro.commands.Command;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.Limit;

import java.util.ArrayList;
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

    //@@author Kushalshah0402
    public void showEnterAmountPrompt() {
        showLine();
        System.out.println("What is your expense amount?");
    }
    //@@author zihaoalt
    public void showEnterIndexPrompt() {
        showLine();
        System.out.println("Enter the expense index to delete, or type -l to list expenses in this category.");
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
    public void showCategoryExpenses(String category, List<Expense> expenses) {
        for (int i = 0; i < expenses.size(); i++) {
            Expense expense = expenses.get(i);
            String toPrint = "Expense " + (i + 1) + " :" + expense.toString();
            showLine();
            System.out.println(toPrint);
            showLine();
        }
    }
    //@@author WangZX2001
    public void showEnterAmountPrompt(String action) {
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
    }

    //@@author Kushalshah0402
    public void showEnterCategoryPrompt() {
        System.out.println("Enter the category:");
    }
    //@@author zihaoalt
    public void showEnterCategoryOptionPrompt() {
        System.out.println("Enter the category, or type -l to list all categories.");
    }
    //@@author Kushalshah0402
    public void showEnterDatePrompt() {
        System.out.println("Enter the date (yyyy-MM-dd):");
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
        System.out.println("⚠️ " + message);
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
        System.out.println("Are you sure you want to change your monthly budget limit to "
                + String.format("$%.2f", limit) + "? [yes/no]");
    }

    //@@author natmloclam
    public void showCancelChangeLimitMessage() {
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
}
