package seedu.finbro.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.finbro.finances.ExpenseList;
import seedu.finbro.finances.Limit;
import seedu.finbro.ui.Ui;

//@@author AK47ofCode
/**
 * Evaluates budget state and shows the corresponding warning message.
 */
public class BudgetWarningService {

    private static final Logger logger = Logger.getLogger(BudgetWarningService.class.getName());

    /**
     * Checks the current expenditure against the set limit and shows appropriate warnings
     * if the limit is close to being exceeded or has been exceeded.
     *
     * @param expenses The list of expenses to evaluate.
     * @param ui The UI instance to display warnings.
     */
    public void checkAndShowWarnings(ExpenseList expenses, Ui ui) {
        double monthlyTotal = expenses.getCurrentMonthTotalExpenditure();
        logger.log(Level.INFO, "Monthly Total Expenditure: " + monthlyTotal);

        if (monthlyTotal - Limit.getLimit() > 0) {
            logger.log(Level.INFO, "Exceeded monthly budget limit: " + Limit.getLimit());
            ui.showBudgetExceeded(Limit.getLimit());
        } else if (Limit.getLimit() - monthlyTotal <= 20) {
            logger.log(Level.INFO, "Approaching monthly budget limit: " + Limit.getLimit());
            ui.showBudgetReminder(Limit.getLimit());
        } else {
            logger.log(Level.INFO, "Within monthly budget limit: " + Limit.getLimit());
        }
    }
}
