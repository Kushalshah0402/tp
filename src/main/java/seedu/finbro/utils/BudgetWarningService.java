package seedu.finbro.utils;

import seedu.finbro.finances.ExpenseList;
import seedu.finbro.finances.Limit;
import seedu.finbro.ui.Ui;

//@@author AK47ofCode
/**
 * Evaluates budget state and shows the corresponding warning message.
 */
public class BudgetWarningService {

    /**
     * Checks the current expenditure against the set limit and shows appropriate warnings
     * if the limit is close to being exceeded or has been exceeded.
     *
     * @param expenses The list of expenses to evaluate.
     * @param ui The UI instance to display warnings.
     */
    public void checkAndShowWarnings(ExpenseList expenses, Ui ui) {
        double monthlyTotal = expenses.getCurrentMonthTotalExpenditure();
        System.out.println("Monthly Total: " + monthlyTotal);

        if (monthlyTotal - Limit.getLimit() > 0) {
            ui.showBudgetExceeded(Limit.getLimit());
        } else if (Limit.getLimit() - monthlyTotal <= 20) {
            ui.showBudgetReminder(Limit.getLimit());
        } else {
            return;
        }
    }
}
