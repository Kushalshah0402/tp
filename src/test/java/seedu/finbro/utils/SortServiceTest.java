package seedu.finbro.utils;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.Expense;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortServiceTest {

    @Test
    void sortExpenses_month_sortsChronologically() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = SortService.sortExpenses(expenses, "month");

        assertEquals(List.of(
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026")
        ), sorted);
    }

    @Test
    void sortExpenses_category_sortsAlphabeticallyThenAmount() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = SortService.sortExpenses(expenses, "category");

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(4.0, "transport", "20 January 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026")
        ), sorted);
    }

    @Test
    void sortExpenses_amount_sortsDescending() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> sorted = SortService.sortExpenses(expenses, "amount");

        assertEquals(List.of(
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ), sorted);
    }

    @Test
    void sortExpenses_caseInsensitiveSortType_supported() throws FinbroException {
        List<Expense> expenses = createSampleExpenses();

        List<Expense> monthSorted = SortService.sortExpenses(expenses, "MONTH");
        List<Expense> categorySorted = SortService.sortExpenses(expenses, "Category");
        List<Expense> amountSorted = SortService.sortExpenses(expenses, "aMoUnT");

        assertEquals(SortService.sortExpenses(expenses, "month"), monthSorted);
        assertEquals(SortService.sortExpenses(expenses, "category"), categorySorted);
        assertEquals(SortService.sortExpenses(expenses, "amount"), amountSorted);
    }

    @Test
    void sortExpenses_invalidSort_throwsException() {
        List<Expense> expenses = createSampleExpenses();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> SortService.sortExpenses(expenses, "year"));

        assertEquals("Invalid sort type. Supported sorts: month, category, amount", exception.getMessage());
    }

    @Test
    void isValidSortType_knownTypesTrue_unknownFalse() {
        assertTrue(SortService.isValidSortType("month"));
        assertTrue(SortService.isValidSortType("category"));
        assertTrue(SortService.isValidSortType("amount"));
        assertTrue(SortService.isValidSortType("MONTH"));
        assertFalse(SortService.isValidSortType("year"));
        assertFalse(SortService.isValidSortType(""));
    }

    @Test
    void sortExpenses_doesNotMutateInputList_returnsNewList() throws FinbroException {
        List<Expense> original = createSampleExpenses();
        List<Expense> originalSnapshot = new ArrayList<>(original);

        List<Expense> sorted = SortService.sortExpenses(original, "amount");

        assertEquals(originalSnapshot, original);
        assertNotSame(original, sorted);
    }

    private List<Expense> createSampleExpenses() {
        return new ArrayList<>(List.of(
                new Expense(12.0, "transport", "3 February 2026"),
                new Expense(20.0, "food", "15 March 2026"),
                new Expense(10.0, "utilities", "5 January 2026"),
                new Expense(4.0, "transport", "20 January 2026")
        ));
    }
}
