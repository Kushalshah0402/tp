package seedu.finbro;

import org.junit.jupiter.api.Test;
import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;
import seedu.finbro.exception.FinbroException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpenseListTest {
    @Test
    void add_expenseAdded_sizeIncreases() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        assertEquals(1, list.size());
    }

    @Test
    void getAll_returnsAllExpenses() {
        ExpenseList list = new ExpenseList();
        Expense e = new Expense(10, "food", "1 Jan 2026");
        list.add(e);
        List<Expense> all = list.getAll();
        assertEquals(1, all.size());
        assertTrue(all.contains(e));
    }

    @Test
    void getCategoryExpenses_correctCategory_returnsOnlyMatching() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        list.add(new Expense(5, "transport", "2 Jan 2026"));
        list.add(new Expense(7, "food", "3 Jan 2026"));
        List<Expense> foodExpenses = list.getCategoryExpenses("food");
        assertEquals(2, foodExpenses.size());
    }

    @Test
    void removeByCategoryIndex_valid_removesCorrectExpense() throws Exception {
        ExpenseList list = new ExpenseList();
        Expense e1 = new Expense(10, "food", "1 Jan 2026");
        Expense e2 = new Expense(7, "food", "3 Jan 2026");
        list.add(e1);
        list.add(e2);
        Expense removed = list.removeByCategoryIndex("food", 2);
        assertEquals(e2, removed);
        assertEquals(1, list.size());
    }

    @Test
    void removeByCategoryIndex_invalidNumber_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("food", 0));
    }

    @Test
    void removeByCategoryIndex_categoryNotFound_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("transport", 1));
    }

    @Test
    void removeByCategoryIndex_outOfBounds_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("food", 2));
    }

    @Test
    void getTotalExpenditure_returnsTotalExpenditure() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        list.add(new Expense(5, "transport", "2 Jan 2026"));
        list.add(new Expense(7, "food", "3 Jan 2026"));

        assertEquals(22, list.getTotalExpenditure());
    }

    @Test
    void getRemainingExpenditure_withLimit_returnsCorrectRemaining() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "1 Jan 2026"));
        Limit.initLimit(20);

        assertEquals(10, list.getRemainingExpenditure());
    }
}
