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

    @Test
    public void execute_viewAll_showsAllExpenses() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all").execute(expenses, ui, null);

        assertEquals(1, ui.showAllExpensesCallCount);
        assertEquals(expenses.getAll(), ui.lastShownExpenses);
    }

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

    @Test
    public void execute_viewAllFilterAmount_sortedByHighestAmount() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -filter amount").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), ui.lastShownExpenses);
    }

    @Test
    public void execute_viewAllFilterMonth_sortedChronologically() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -filter month").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026")
        ), ui.lastShownExpenses);
    }

    @Test
    public void execute_viewAllFilterCategory_sortedAlphabeticallyByCategory() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("all -filter category").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026")
        ), ui.lastShownExpenses);
    }

    @Test
    public void execute_viewCategoryFilterMonth_sortedChronologically() throws FinbroException {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        new ViewCommand("transport -filter month").execute(expenses, ui, null);

        assertEquals(List.of(
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026")
        ), ui.lastShownExpenses);
    }

    @Test
    public void execute_viewCategoryFilterCategory_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("transport -filter category").execute(expenses, ui, null));

        assertEquals("Category filter is only supported with \"view all\".", exception.getMessage());
    }

    @Test
    public void execute_viewAllInvalidFilter_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("all -filter year").execute(expenses, ui, null));

        assertEquals("Invalid filter type: year\nSupported filters: month, category, amount", exception.getMessage());
    }

    @Test
    public void execute_viewWithMultipleFilterTags_throwsException() {
        ExpenseList expenses = createSampleExpenses();
        CaptureUi ui = new CaptureUi();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> new ViewCommand("all -filter amount -filter month").execute(expenses, ui, null));

        assertEquals("Invalid format: use at most one -filter tag.", exception.getMessage());
    }

    private ExpenseList createSampleExpenses() {
        ExpenseList expenses = new ExpenseList();
        expenses.add(new Expense(12.0, "transport", "3 February 2026"));
        expenses.add(new Expense(20.0, "food", "15 March 2026"));
        expenses.add(new Expense(10.0, "utilities", "5 January 2026"));
        expenses.add(new Expense(4.0, "transport", "20 January 2026"));
        return expenses;
    }

    private static class CaptureUi extends Ui {
        private List<Expense> lastShownExpenses = List.of();
        private int showAllExpensesCallCount = 0;

        @Override
        public void showAllExpenses(List<Expense> expenses) {
            showAllExpensesCallCount++;
            lastShownExpenses = expenses;
        }
    }
}


