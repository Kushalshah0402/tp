package seedu.finbro;

import seedu.finbro.commands.Expense;

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
        System.out.print("> ");
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

    public void showAllExpenses(List<Expense> expenses) {
        showLine();
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            System.out.println("Here are your expenses:");
            System.out.println();
            for (int i = 0; i < expenses.size(); i++) {
                System.out.println((i + 1) + "." + expenses.get(i));
                System.out.println();
            }
        }
        showLine();
    }

    private void showLine() {
        System.out.println(LINE);
    }
}
