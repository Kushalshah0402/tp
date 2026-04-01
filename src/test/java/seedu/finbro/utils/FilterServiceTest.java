package seedu.finbro.utils;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterServiceTest {

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_caseInsensitive_filtersMatchingExpenses() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> filtered = FilterService.filterExpensesByMonth(expenses, "jAnUaRy");

        assertEquals(List.of(
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), filtered);
    }

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_noMatches_returnsEmptyList() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> filtered = FilterService.filterExpensesByMonth(expenses, "April");

        assertTrue(filtered.isEmpty());
    }

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_invalidMonth_throwsException() {
        List<Expense> expenses = createSampleExpenses();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> FilterService.filterExpensesByMonth(expenses, "jan"));

        assertEquals("Invalid month for -filter: jan\nUse full month name, e.g. January.", exception.getMessage());
    }

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_corruptedExpenseDate_throwsException() {
        List<Expense> expenses = createSampleExpenses();
        expenses.add(new Expense(5.0, "misc", "bad date"));

        FinbroException exception = assertThrows(FinbroException.class,
                () -> FilterService.filterExpensesByMonth(expenses, "January"));

        assertEquals("Corrupted expense: Invalid date format", exception.getMessage());
    }

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_doesNotMutateInputList_returnsNewList() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();
        List<Expense> snapshot = new ArrayList<>(expenses);

        List<Expense> filtered = FilterService.filterExpensesByMonth(expenses, "January");

        assertEquals(snapshot, expenses);
        assertNotSame(expenses, filtered);
    }

    //@@author AK47ofCode
    @Test
    void filterExpensesByMonth_caseInsensitiveMonthName_supported() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> januaryFiltered = FilterService.filterExpensesByMonth(expenses, "JANUARY");
        List<Expense> januaryFilteredLower = FilterService.filterExpensesByMonth(expenses, "january");

        assertEquals(januaryFilteredLower, januaryFiltered);
    }

    //@@author AK47ofCode
    private List<Expense> createSampleExpenses() {
        return new ArrayList<>(List.of(
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ));
    }
}


