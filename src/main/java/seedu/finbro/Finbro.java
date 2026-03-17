package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.exception.FinbroException;

import java.util.List;

public class Finbro {
    private Storage storage;
    private ExpenseList expenses;
    private Ui ui;

    public Finbro(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            List<Expense> loadedExpenses = storage.load();
            expenses = new ExpenseList(loadedExpenses);
        } catch (FinbroException e) {
            ui.showError("Error loading expenses. Starting with empty list.");
            expenses = new ExpenseList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                if (expenses.getRemainingExpenditure() <= 20 && expenses.size() > 0) {
                    ui.showBudgetReminder();
                }
                String input = ui.readCommand();
                if (input.equalsIgnoreCase("exit")) {
                    storage.save(expenses.getAll());
                    ui.showGoodbye();
                    isExit = true;
                } else {
                    Parser.parse(input, expenses, ui);
                    storage.save(expenses.getAll());
                }

            } catch (FinbroException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new Finbro("./data/finbro.txt").run();
    }
}
