package seedu.finbro.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.utils.Limit;

public class SetLimitCommand extends Command {
    private static final Logger logger = Logger.getLogger(SetLimitCommand.class.getName());

    @Override
    public void execute(String input, ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        String[] parts = input.split(" ", 2);
        if (parts.length < 2) {
            logger.log(Level.INFO, "Display current limit");
            ui.showLimit();
            return;
        }

        logger.log(Level.INFO, "Attempting to set limit to: {0}", parts[1]);
        double limit;
        // check if limit is of valid type
        try {
            limit = Double.parseDouble(parts[1]);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid limit input (not a number): {0}", parts[1]);
            throw new FinbroException("Monthly spending limit must be a number");
        }

        // check if limit is a valid value
        if (limit < 0) {
            logger.log(Level.WARNING, "Invalid limit input (out of valid range): {0}", parts[1]);
            throw new FinbroException("Monthly spending limit must be at least $0");
        }
        assert limit >= 0;

        confirmLimitChange(ui, limit);

        ui.showLimit();
    }

    static void confirmLimitChange(Ui ui, double limit) {
        ui.showChangeLimitWarning(limit);
        logger.log(Level.INFO, "Getting confirmation for limit change");
        String confirm = ui.readCommand();
        if (confirm.equals("yes")) {
            logger.log(Level.INFO, "Confirmation message: \"{0}\", limit change accepted", confirm);
            assert limit >= 0;
            Limit.setLimit(limit);
        } else if (confirm.equals("no")) {
            logger.log(Level.INFO, "Confirmation message: \"{0}\", limit change rejected", confirm);
            ui.showCancelChangeLimitMessage();
        } else {
            logger.log(Level.WARNING, "Invalid confirmation message: \"{0}\", limit change rejected", confirm);
            ui.showCancelChangeLimitMessage();
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Sets the monthly spending limit.
                Format: limit <amount>
                Use: Creates or replaces the current monthly budget limit.
                Note: amount must be positive.""";
    }
}
