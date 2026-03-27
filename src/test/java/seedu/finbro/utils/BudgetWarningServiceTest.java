package seedu.finbro.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seedu.finbro.ui.Ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BudgetWarningServiceTest {

    private final BudgetWarningService service = new BudgetWarningService();

    //@@author AK47ofCode
    @BeforeEach
    void resetLimit() {
        Limit.setLimit(0);
    }

    //@@author AK47ofCode
    @Test
    void checkAndShowWarnings_limitExceeded_showsExceededWarning() {
        ExpenseList expenses = new ExpenseList();
        expenses.add(new Expense(120, "food", "2026-03-01"));
        Limit.setLimit(100);
        TestUi ui = new TestUi();

        service.checkAndShowWarnings(expenses, ui);

        assertTrue(ui.wasExceededShown);
        assertFalse(ui.wasReminderShown);
    }

    //@@author AK47ofCode
    @Test
    void checkAndShowWarnings_closeToLimit_showsReminderWarning() {
        ExpenseList expenses = new ExpenseList();
        expenses.add(new Expense(85, "food", "2026-03-01"));
        Limit.setLimit(100);
        TestUi ui = new TestUi();

        service.checkAndShowWarnings(expenses, ui);

        assertFalse(ui.wasExceededShown);
        assertTrue(ui.wasReminderShown);
    }

    //@@author AK47ofCode
    @Test
    void checkAndShowWarnings_limitNotClose_showsNoWarning() {
        ExpenseList expenses = new ExpenseList();
        expenses.add(new Expense(70, "food", "2026-03-01"));
        Limit.setLimit(100);
        TestUi ui = new TestUi();

        service.checkAndShowWarnings(expenses, ui);

        assertFalse(ui.wasExceededShown);
        assertFalse(ui.wasReminderShown);
    }

    //@@author AK47ofCode
    @Test
    void checkAndShowWarnings_noExpenses_showsNoWarning() {
        ExpenseList expenses = new ExpenseList();
        Limit.setLimit(100);
        TestUi ui = new TestUi();

        service.checkAndShowWarnings(expenses, ui);

        assertFalse(ui.wasExceededShown);
        assertFalse(ui.wasReminderShown);
    }

    private static class TestUi extends Ui {
        private boolean wasReminderShown;
        private boolean wasExceededShown;

        //@@author AK47ofCode
        @Override
        public void showBudgetReminder(double limit) {
            wasReminderShown = true;
        }

        //@@author AK47ofCode
        @Override
        public void showBudgetExceeded(double limit) {
            wasExceededShown = true;
        }
    }
}

