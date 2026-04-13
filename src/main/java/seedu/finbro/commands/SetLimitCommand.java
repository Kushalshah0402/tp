package seedu.finbro.commands;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.finbro.finances.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.Limit;

public class SetLimitCommand extends Command {
    private static final Logger logger = Logger.getLogger(SetLimitCommand.class.getName());

    private final String arg;

    public SetLimitCommand(String arg) {
        this.arg = arg;
    }

    //@@author natmloclam
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        if (arg.isEmpty()) {
            logger.log(Level.INFO, "Display current limit");
            ui.showLimit();
            return;
        }

        logger.log(Level.INFO, "Attempting to set limit to: {0}", arg);

        double inputLimit = verifyLimitType(arg);
        double limit = verifyLimitRange(inputLimit);
        assert limit > 0;

        confirmLimitChange(ui, limit);

        ui.showLimit();
    }

    //@@author natmloclam
    private static double verifyLimitRange(double limit) throws FinbroException {
        double roundedLimit = roundToTwoDp(limit);

        if (roundedLimit < 0) {
            logger.log(Level.WARNING, "Invalid limit input (negative): {0}", roundedLimit);
            throw new FinbroException("Monthly spending limit must be positive");
        } else if (roundedLimit >= 0 && roundedLimit < 0.01) {
            logger.log(Level.WARNING, "Invalid limit input (zero): {0}", roundedLimit);
            throw new FinbroException(
                    "Monthly spending limit must be greater than $0.00 (rounded to 2 decimal places)"
            );
        }
        return roundedLimit;
    }

    private static double roundToTwoDp(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    //@@author natmloclam
    private static double verifyLimitType(String inputLimit) throws FinbroException {
        double limit;
        try {
            limit = Double.parseDouble(inputLimit);
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Invalid limit input (not a number): {0}", inputLimit);
            throw new FinbroException("Monthly spending limit must be a number");
        }
        return limit;
    }

    //@@author natmloclam
    static void confirmLimitChange(Ui ui, double limit) {
        ui.showChangeLimitWarning(limit);
        logger.log(Level.INFO, "Getting confirmation for limit change");

        String confirm = ui.readCommand().toLowerCase();
        if (confirm.equals("yes") || confirm.equals("y")) {
            logger.log(Level.INFO, "Confirmation message: \"{0}\", limit change accepted", confirm);
            assert limit > 0;
            Limit.setLimit(limit);
        } else if (confirm.equals("no") || confirm.equals("n")) {
            logger.log(Level.INFO, "Confirmation message: \"{0}\", limit change rejected", confirm);
            ui.showCancelChangeLimitMessage();
        } else {
            logger.log(Level.WARNING, "Invalid confirmation message: \"{0}\", limit change rejected", confirm);
            ui.showCancelChangeLimitMessage();
        }
    }

    //@@author natmloclam
    @Override
    public String getHelpMessage() {
        return """
                Sets the monthly spending limit.
                Format: limit <amount>
                Use: Creates or replaces the current monthly budget limit.
                Note: amount must be positive.""";
    }

    @Override
    public boolean checksBudget() {
        return true;
    }
}
