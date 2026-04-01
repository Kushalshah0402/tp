package seedu.finbro.utils;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FilterServiceTest {

    //@@author AK47ofCode
    @Test
    void filterExpenses_month_sortsChronologically() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = FilterService.filterExpenses(expenses, "month");

        assertEquals(List.of(
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026")
        ), sorted);
    }

    //@@author AK47ofCode
    @Test
    void filterExpenses_category_sortsAlphabeticallyThenAmount() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = FilterService.filterExpenses(expenses, "category");

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026")
        ), sorted);
    }

    //@@author AK47ofCode
    @Test
    void filterExpenses_amount_sortsDescending() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = FilterService.filterExpenses(expenses, "amount");

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), sorted);
    }

    //@@author AK47ofCode
    @Test
    void filterExpenses_caseInsensitiveFilterType_supported() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> monthSorted = FilterService.filterExpenses(expenses, "MONTH");
        List<Expense> categorySorted = FilterService.filterExpenses(expenses, "Category");
        List<Expense> amountSorted = FilterService.filterExpenses(expenses, "aMoUnT");

        assertEquals(FilterService.filterExpenses(expenses, "month"), monthSorted);
        assertEquals(FilterService.filterExpenses(expenses, "category"), categorySorted);
        assertEquals(FilterService.filterExpenses(expenses, "amount"), amountSorted);
    }

    //@@author AK47ofCode
    @Test
    void filterExpenses_invalidFilter_throwsException() {
        List<Expense> expenses = createSampleExpenses();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> FilterService.filterExpenses(expenses, "year"));

        assertEquals("Invalid filter type. Supported filters: month, category, amount", exception.getMessage());
    }

    //@@author AK47ofCode
    @Test
    void isValidFilterType_knownTypesTrue_unknownFalse() {
        assertTrue(FilterService.isValidFilterType("month"));
        assertTrue(FilterService.isValidFilterType("category"));
        assertTrue(FilterService.isValidFilterType("amount"));
        assertTrue(FilterService.isValidFilterType("MONTH"));
        assertFalse(FilterService.isValidFilterType("year"));
        assertFalse(FilterService.isValidFilterType(""));
    }

    //@@author AK47ofCode
    @Test
    void filterExpenses_doesNotMutateInputList_returnsNewList() throws FinbroException {
        List<Expense> original = createSampleExpenses();
        List<Expense> originalSnapshot = new ArrayList<>(original);

        List<Expense> sorted = FilterService.filterExpenses(original, "amount");

        assertEquals(originalSnapshot, original);
        assertNotSame(original, sorted);
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


