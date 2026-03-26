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

    private final String arg;

    public SetLimitCommand(String arg) {
        this.arg = arg;
    }

    /**
     * @author natmloclam
     */
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        if (arg.isEmpty()) {
            logger.log(Level.INFO, "Display current limit");
            ui.showLimit();
            return;
        }

        logger.log(Level.INFO, "Attempting to set limit to: {0}", arg);

        double limit = verifyLimitType(arg);
        verifyLimitRange(limit);
        assert limit >= 0;

        confirmLimitChange(ui, limit);

        ui.showLimit();
    }
    /**
     * @author natmloclam
     */
    private static void verifyLimitRange(double limit) throws FinbroException {
        if (limit < 0) {
            logger.log(Level.WARNING, "Invalid limit input (out of valid range): {0}", limit);
            throw new FinbroException("Monthly spending limit must be at least $0");
        }
    }
    /**
     * @author natmloclam
     */
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
    /**
     * @author natmloclam
     */
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
    /**
     * @author natmloclam
     */
    @Override
    public String getHelpMessage() {
        return """
                Sets the monthly spending limit.
                Format: limit <amount>
                Use: Creates or replaces the current monthly budget limit.
                Note: amount must be positive.""";
    }
}
