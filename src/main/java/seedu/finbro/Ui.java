package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;

import java.util.List;
import java.util.Scanner;

public class Ui {
    private static final String LINE = "--------------------------------------------------";
    private final Scanner scanner = new Scanner(System.in);

    public void showWelcome() {
        showLine();
        System.out.println("Hello Finbro here for you!");
        System.out.println("Type help to see all available commands.");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye! See you again.");
        showLine();
    }

    public String readCommand() {
        if (!scanner.hasNextLine()) {
            return "exit";
        }
        return scanner.nextLine().trim();
    }

    public void showError(String message) {
        showLine();
        System.out.println("Error: " + message);
        showLine();
    }

    public void showExpenseAdded(Expense e, int total) {
        showLine();
        System.out.println("Got it. I've added this expense:");
        System.out.println("  " + e);
        System.out.println("Now you have " + total + " expenses.");
        showLine();
    }

    public void showExpenseRemoved(Expense e, int total) {
        showLine();
        System.out.println("Got it. I've removed this expense:");
        System.out.println("  " + e);
        System.out.println("Now you have " + total + " expenses.");
        showLine();
    }

    public void showAllExpenses(List<Expense> expenses) {
        showLine();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            double total = 0;
            System.out.println("Here are your expenses:");
            System.out.println();
            for (int i = 0; i < expenses.size(); i++) {
                total += expenses.get(i).getAmount();
                System.out.println((i + 1) + ". " + expenses.get(i));
                System.out.println();
            }
            System.out.printf("Total expenditure: $%.2f\n", total);
        }
        showLine();
    }

    public void showLimit() {
        showLine();
        System.out.println("Monthly budget limit: " + String.format("$%.2f", Limit.getLimit()));
        showLine();
    }

    public void showChangeLimitWarning(double limit) {
        System.out.println("Are you sure you want to change your monthly budget limit to "
                + String.format("$%.2f", limit) + "? [yes/no]");
    }

    public void showCancelChangeLimitMessage() {
        System.out.println("Monthly budget limit was not changed");
    }

    public void showLimitEditMenu(double currentLimit) {
        showLine();
        System.out.println("Current monthly budget limit: " + String.format("$%.2f", currentLimit));
        System.out.println("Choose an action:");
        System.out.println("1. Increase limit");
        System.out.println("2. Decrease limit");
        System.out.println("3. Replace limit");
        showLine();
    }

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

    public void showBudgetReminder() {
        showLine();
        System.out.println("You are close to your monthly spending limit!");
        showLine();
    }

    private void showLine() {
        System.out.println(LINE);
    }
}
