package seedu.finbro.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import seedu.finbro.exception.FinbroException;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpenseListTest {
    @BeforeEach
    void resetLimitState() {
        Limit.setLimit(0);
    }

    //@@author Kushalshah0402
    @Test
    void add_expenseAdded_sizeIncreases() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        assertEquals(1, list.size());
    }
    //@@author Kushalshah0402
    @Test
    void getAll_returnsAllExpenses() {
        ExpenseList list = new ExpenseList();
        Expense e = new Expense(10, "food", "2026-01-01");
        list.add(e);
        List<Expense> all = list.getAll();
        assertEquals(1, all.size());
        assertTrue(all.contains(e));
    }
    //@@author Kushalshah0402
    @Test
    void getCategoryExpenses_correctCategory_returnsOnlyMatching() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        list.add(new Expense(5, "transport", "2026-01-02"));
        list.add(new Expense(7, "food", "2026-01-03"));
        List<Expense> foodExpenses = list.getCategoryExpenses("food");
        assertEquals(2, foodExpenses.size());
    }
    //@@author Kushalshah0402
    @Test
    void removeByCategoryIndex_valid_removesCorrectExpense() throws Exception {
        ExpenseList list = new ExpenseList();
        Expense e1 = new Expense(10, "food", "2026-01-03");
        Expense e2 = new Expense(7, "food", "2026-01-03");
        list.add(e1);
        list.add(e2);
        Expense removed = list.removeByCategoryIndex("food", 2);
        assertEquals(e2, removed);
        assertEquals(1, list.size());
    }
    //@@author Kushalshah0402
    @Test
    void removeByCategoryIndex_invalidNumber_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("food", 0));
    }
    //@@author Kushalshah0402
    @Test
    void removeByCategoryIndex_categoryNotFound_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("transport", 1));
    }
    //@@author Kushalshah0402
    @Test
    void removeByCategoryIndex_outOfBounds_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        assertThrows(FinbroException.class,
                () -> list.removeByCategoryIndex("food", 2));
    }
    //@@author AK47ofCode
    @Test
    void getTotalExpenditure_returnsTotalExpenditure() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        list.add(new Expense(5, "transport", "2026-01-02"));
        list.add(new Expense(7, "food", "2026-01-03"));

        assertEquals(22, list.getTotalExpenditure());
    }
    //@@author AK47ofCode
    @Test
    void expenseListFromLoadedExpenses_initializesCorrectTotalAndRemaining() {
        List<Expense> loadedExpenses = new ArrayList<>();
        loadedExpenses.add(new Expense(10, "food", "2026-01-01"));
        loadedExpenses.add(new Expense(5, "transport", "2026-01-02"));
        loadedExpenses.add(new Expense(7, "food", "2026-01-03"));
        Limit.setLimit(30);

        ExpenseList list = new ExpenseList(loadedExpenses);

        assertEquals(22, list.getTotalExpenditure());
        assertEquals(8, list.getRemainingExpenditure());
    }
    //@@author AK47ofCode
    @Test
    void getRemainingExpenditure_withLimit_returnsCorrectRemaining() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        Limit.setLimit(20);

        assertEquals(10, list.getRemainingExpenditure());
    }
    //@@author AK47ofCode
    @Test
    void getRemainingExpenditure_deleteExpense_returnsCorrectRemaining() throws FinbroException {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "2026-01-01"));
        list.add(new Expense(5, "transport", "2026-01-02"));
        list.add(new Expense(7, "food", "2026-01-03"));
        list.add(new Expense(8, "food", "2026-01-04"));
        Limit.setLimit(20);
        list.removeByCategoryIndex("food", 2);

        assertEquals(-3, list.getRemainingExpenditure());
    }

    //@@author natmloclam
    @Test
    void parseMonth_validDateFormat_correctYearYearMonth() throws FinbroException {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "3 March 2026"));

        assertEquals(YearMonth.of(2026, 3), list.parseYearMonth(list.get(0)));
    }

    //@@author natmloclam
    @Test
    void parseYearMonth_invalidDateFormat_throwsException() {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "3 March"));

        Exception e = assertThrows(FinbroException.class, () -> list.parseYearMonth(list.get(0)));

        String expectedMessage = "Invalid date format";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    //@@author natmloclam
    @Test
    void getMonthlyExpenses_validExpenses_correctMap() throws FinbroException {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "3 March 2026"));
        list.add(new Expense(5, "transport", "2 January 2026"));
        list.add(new Expense(7, "food", "2 January 2026"));

        Map<YearMonth, Double> correctOutput = new HashMap<>();
        correctOutput.put(YearMonth.of(2026, 3), 10.0);
        correctOutput.put(YearMonth.of(2026, 1), 12.0);

        assertEquals(correctOutput, list.getMonthlyExpenses());
    }

    //@@author natmloclam
    @Test
    void getMonthlyExpenses_invalidDate_ignoreExpense() throws FinbroException {
        ExpenseList list = new ExpenseList();
        list.add(new Expense(10, "food", "3 March 2026"));
        list.add(new Expense(5, "transport", "2 January 2026"));
        list.add(new Expense(7, "food", "2 bleh 2026"));

        Map<YearMonth, Double> correctOutput = new HashMap<>();
        correctOutput.put(YearMonth.of(2026, 3), 10.0);
        correctOutput.put(YearMonth.of(2026, 1), 5.0);

        assertEquals(correctOutput, list.getMonthlyExpenses());
    }


}
