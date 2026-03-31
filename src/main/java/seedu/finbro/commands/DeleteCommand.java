package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.Expense;


import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DeleteCommand extends Command {
    private static final Logger logger = Logger.getLogger(DeleteCommand.class.getName());
    private final String arg;

    public DeleteCommand(String arg) {
        this.arg = arg;
    }

    //@@author zihaoalt
    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {

        //Walkthrough mode
        if (arg.isBlank()) {
            logger.log(Level.INFO, "Entering delete walkthrough mode");
            runWalkthrough(expenses, ui);
            return;
        }

        //Strict mode (existing behavior)
        logger.log(Level.INFO, "Entering delete strict mode");
        verifyInputLength(arg);
        String category = filterCategory(arg);
        int index = verifyIndex(arg);

        Expense expense;
        try {
            expense = expenses.removeByCategoryIndex(category, index);
        } catch (FinbroException e) {
            logger.log(Level.WARNING, e.getMessage());
            throw e;
        }

        logger.log(Level.INFO, "Successfully deleted expense in category " + category + " #" + index);
        ui.showExpenseRemoved(expense, expenses.size());
    }
    //@@author zihaoalt
    private void verifyInputLength(String input) throws FinbroException {
        String [] parts = input.split(" ");
        if (parts.length < 2) {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException("Usage: delete <category> <number>");
        }
    }
    //@@author zihaoalt
    private int verifyIndex(String input) throws FinbroException {
        String[] parts = input.split(" ");
        logger.log(Level.INFO, "Attempting to delete expense in category " + parts[0] + " #" + parts[1]);

        int index;
        try {
            index = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid category index number");
            throw new FinbroException("Expense number must be a number.");
        }
        return index;
    }
    //@@author zihaoalt
    private String filterCategory(String input) throws FinbroException {
        String[] parts = input.split(" ");
        return parts[0];
    }
    //@@author zihaoalt
    private void runWalkthrough(ExpenseList expenses, Ui ui) throws FinbroException {
        String category;
        int index;
        List<Expense> categoryList;
        List<String> categoryNames;


        // CATEGORY LOOP
        while (true) {
            ui.showEnterCategoryOptionPrompt();
            category = ui.readCommand();
            if (category == null) {
                logger.log(Level.WARNING, "UI returned null while reading delete category input");
                throw new FinbroException("Failed to read category input.");
            }
            category = category.trim();

            if (category.isBlank()) {
                logger.log(Level.WARNING, "Delete walkthrough received blank category input");
                ui.showInlineError("Category cannot be empty.");
                continue;
            }

            if (category.equals("-l")) {
                categoryNames = expenses.getAllCategoryNames();
                logger.log(Level.INFO, "Successfully walked through all category names");
                ui.showAllCategoryNames(categoryNames);
                continue;
            }

            categoryList = expenses.getCategoryExpenses(category);
            if (categoryList.isEmpty()) {
                logger.log(Level.WARNING, "Delete walkthrough category not found: \"{0}\"", category);
                ui.showInlineError("Category " + category + " does not exist.");
                continue;
            }
            break;
        }

        // INDEX LOOP
        while (true) {
            ui.showEnterIndexPrompt();
            String input = ui.readCommand();
            if (input == null) {
                logger.log(Level.WARNING, "UI returned null while reading delete index input");
                throw new FinbroException("Failed to read index input.");
            }
            input = input.trim();

            if (input.equalsIgnoreCase("-l")) {
                ui.showCategoryExpenses(category, categoryList);
                continue;
            }

            try {
                index = Integer.parseInt(input);
                if (index <= 0 || index > categoryList.size()) {
                    ui.showInlineError("Index out of bounds.");
                    continue;
                }
                break;
            } catch (NumberFormatException e) {
                ui.showInlineError("Enter a valid index number, or type -l to list expenses.");
            }
        }

        // CONFIRMATION
        Expense expense = expenses.getExpenseByCategoryIndex(categoryList, index);
        ui.showConfirmExpense(expense);
        String confirm = ui.readCommand();
        if (confirm == null) {
            logger.log(Level.WARNING, "UI returned null while reading delete confirmation input");
            throw new FinbroException("Failed to read confirmation input.");
        }
        confirm = confirm.trim();
        if (confirm.equalsIgnoreCase("yes") || confirm.equalsIgnoreCase("y")) {
            logger.log(Level.INFO, "User confirmed deletion for category \"{0}\" index {1}",
                    new Object[]{category, index});
            expense = expenses.removeByCategoryIndex(category, index);
            ui.showExpenseRemoved(expense, expenses.size());
        } else {
            logger.log(Level.INFO, "User cancelled delete walkthrough for category \"{0}\" index {1}",
                    new Object[]{category, index});
            ui.showCancelDeleteMessage();
        }

    }
    //@@author zihaoalt
    @Override
    public String getHelpMessage() {
        return """
                Deletes a specific expense from a category.
                Format: delete <category> <number> OR delete
                Use 1: Permanently removes the numbered expense in that category. 
                Use 2:Type delete only will walk through the deletion process.
                Note: use the displayed index number, such as 2.""";
    }
}
