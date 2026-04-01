package seedu.finbro.commands;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ViewCommandTest {

    //@@author AK47ofCode
    @Test
    public void execute_viewAll_showsAllExpenses() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all").execute(expenses, ui, null);

        assertEquals(1, ui.showAllExpensesCallCount);
        assertEquals(expenses.getAll(), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewCategory_showsOnlyCategoryExpenses() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("transport").execute(expenses, ui, null);

        assertEquals(1, ui.showAllExpensesCallCount);
        assertEquals(List.of(
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewAllSortAmount_sortedByHighestAmount() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -sort amount").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewAllSortMonth_sortedChronologically() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -sort month").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026")
        ), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewAllSortCategory_sortedAlphabeticallyByCategory() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -sort category").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026")
        ), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewCategorySortMonth_sortedChronologically() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("transport -sort month").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026")
        ), ui.lastShownExpenses);
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewCategorySortCategory_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("transport -sort category").execute(expenses, ui, null));

        assertEquals("Category sort is only supported with \"view all\".", exception.getMessage());
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewAllInvalidSort_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("all -sort year").execute(expenses, ui, null));

        assertEquals("Invalid sort type: year\nSupported sorts: month, category, amount", exception.getMessage());
    }

    //@@author AK47ofCode
    @Test
    public void execute_viewWithMultipleSortTags_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("all -sort amount -sort month").execute(expenses, ui, null));

        assertEquals("Invalid format: use at most one -sort tag.", exception.getMessage());
    }

    //@@author AK47ofCode
    private ExpenseList createSampleExpenses() {
        ExpenseList expenses = new ExpenseList();
        expenses.add(new Expense(12.0, "transport", "3 February 2026"));
        expenses.add(new Expense(20.0, "food", "15 March 2026"));
        expenses.add(new Expense(10.0, "utilities", "5 January 2026"));
        expenses.add(new Expense(4.0, "transport", "20 January 2026"));
        return expenses;
    }

    //@@author AK47ofCode
    private static class CaptureUi extends Ui {
        private List<Expense> lastShownExpenses = List.of();
        private int showAllExpensesCallCount = 0;

        //@@author AK47ofCode
        @Override
        public void showAllExpenses(List<Expense> expenses) {
            showAllExpensesCallCount++;
            lastShownExpenses = expenses;
        }
    }
}


