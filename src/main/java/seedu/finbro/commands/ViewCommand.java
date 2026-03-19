package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final String COMMAND_VIEW = "view";
    private static final Logger logger = Logger.getLogger(ViewCommand.class.getName());

    @Override
    public void execute(String input, ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        if (input.equals(COMMAND_VIEW)) {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException("Usage: view <category> OR view all");
        } else if (input.equals(COMMAND_VIEW + " " + "all")) {
            logger.log(Level.INFO, "Displaying all expenses");
            ui.showAllExpenses(expenses.getAll());
        } else if (input.startsWith(COMMAND_VIEW + " ")) {
            String category = input.substring((COMMAND_VIEW + " ").length());
            if (expenses.getCategoryExpenses(category).isEmpty()) {
                logger.log(Level.WARNING, "Invalid category name");
                throw new FinbroException("Current View Category only supports exact matches, or empty category.");
            }
            logger.log(Level.INFO, "Displaying expenses in category " + category);
            ui.showAllExpenses(expenses.getCategoryExpenses(category));
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Shows all recorded expenses.
                Format: view all
                Use: Displays every expense grouped by category.
                
                Shows expenses in a specific category.
                Format: view <category>
                Use: Displays only the expenses under the given category.
                Note: category names are case-insensitive.""";
    }
}
