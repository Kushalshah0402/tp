package seedu.finbro.utils;

import seedu.finbro.ui.Ui;

//@@author AK47ofCode
/**
 * Evaluates budget state and shows the corresponding warning message.
 */
public class BudgetWarningService {

    //@@author AK47ofCode
    /**
     * Checks the current expenditure against the set limit and shows appropriate warnings
     * if the limit is close to being exceeded or has been exceeded.
     *
     * @param expenses The list of expenses to evaluate.
     * @param ui The UI instance to display warnings.
     */
    public void checkAndShowWarnings(ExpenseList expenses, Ui ui) {
        double remaining = expenses.getRemainingExpenditure();
        double limit = Limit.getLimit();
        if (limit > 0 && !expenses.isEmpty()) {
            if (remaining < 0) {
                ui.showBudgetExceeded(limit);
            } else if (remaining <= 20) {
                ui.showBudgetReminder(limit);
            }
        }
    }
}
