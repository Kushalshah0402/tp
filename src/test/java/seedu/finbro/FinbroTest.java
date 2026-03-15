package seedu.finbro;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;


class FinbroTest {
    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    void run_remainingBudgetAboveLimit_showsOverspendingWarning() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(180, "entertainment", "1 Jan 2026"));
        Limit.initLimit(200);

        assertTrue(list.getRemainingExpenditure() <= 20);
    }

    @Test
    void run_remainingBudgetBelowLimit_doesNotShowOverspendingWarning() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(179, "entertainment", "1 Jan 2026"));
        Limit.initLimit(200);

        assertFalse(list.getRemainingExpenditure() <= 20);
    }
}
