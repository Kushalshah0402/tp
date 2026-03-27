package seedu.finbro.utils;

import seedu.finbro.ui.Ui;

//@@author AK47ofCode
/**
 * Evaluates budget state and shows the corresponding warning message.
 */
public class BudgetWarningService {

    //@@author AK47ofCode
    public void checkAndShowWarnings(ExpenseList expenses, Ui ui) {
        double remaining = expenses.getRemainingExpenditure();
        double limit = Limit.getLimit();
        if (limit > 0 && expenses.size() > 0) {
            if (remaining < 0) {
                ui.showBudgetExceeded(limit);
            } else if (remaining <= 20) {
                ui.showBudgetReminder(limit);
            }
        }
    }
}
