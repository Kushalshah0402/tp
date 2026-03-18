package seedu.finbro;

import seedu.finbro.commands.Command;
import seedu.finbro.parser.Parser;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.ExpenseList;
import seedu.finbro.utils.Limit;

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
                double remaining = expenses.getRemainingExpenditure();
                double limit = Limit.getLimit();
                if (limit > 0 && expenses.size() > 0) {
                    if (remaining < 0) {
                        ui.showBudgetExceeded(limit);
                    } else if (remaining <= 20) {
                        ui.showBudgetReminder(limit);
                    }
                }
                String input = ui.readCommand();
                if (input.equalsIgnoreCase("exit")) {
                    storage.save(expenses.getAll());
                    ui.showGoodbye();
                    isExit = true;
                } else {
                    Command command = Parser.parse(input, expenses, ui, storage);
                    command.execute(input, expenses, ui, storage);
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
