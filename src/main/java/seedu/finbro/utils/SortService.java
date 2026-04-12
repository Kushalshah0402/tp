package seedu.finbro.utils;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.Expense;
import seedu.finbro.finances.ExpenseList;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author AK47ofCode
/**
 * Service class to handle sorting of expenses.
 */
public class SortService {
    private static final Logger logger = Logger.getLogger(SortService.class.getName());
    private static final String SORT_YEAR = "year";
    private static final String SORT_MONTH = "month";
    private static final String SORT_CATEGORY = "category";
    private static final String SORT_AMOUNT = "amount";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("d MMMM yyyy");

    //@@author AK47ofCode
    /**
     * Sorts a list of expenses based on the specified sort type.
     *
     * @param expenses The list of expenses to sort.
     * @param sortType The type of sort to apply (year, month, category, or amount).
     * @return A sorted list of expenses.
     * @throws FinbroException if the sort type is invalid.
     */
    public static List<Expense> sortExpenses(List<Expense> expenses, String sortType) throws FinbroException {
        logger.log(Level.INFO, "Sorting expenses by: {0}", sortType);

        return switch (sortType.toLowerCase()) {
        case SORT_YEAR -> sortByYear(expenses);
        case SORT_MONTH -> sortByMonth(expenses);
        case SORT_CATEGORY -> sortByCategory(expenses);
        case SORT_AMOUNT -> sortByAmount(expenses);
        default -> throw new FinbroException("Invalid sort type. Supported sorts: year, month, category, amount");
        };
    }

    //@@author AK47ofCode
    /**
     * Sorts expenses by month in chronological order.
     *
     * @param expenses The list of expenses to sort.
     * @return A list of expenses sorted by month.
     */
    private static List<Expense> sortByMonth(List<Expense> expenses) {
        logger.log(Level.INFO, "Sorting expenses by month");
        List<Expense> sorted = new ArrayList<>(expenses);

        sorted.sort((expense1, expense2) -> {
            try {
                YearMonth yearMonth1 = parseYearMonth(expense1);
                YearMonth yearMonth2 = parseYearMonth(expense2);

                int monthCompare = Integer.compare(yearMonth1.getMonthValue(), yearMonth2.getMonthValue());
                if (monthCompare != 0) {
                    return monthCompare;
                }

                int yearCompare = Integer.compare(yearMonth1.getYear(), yearMonth2.getYear());
                if (yearCompare != 0) {
                    return yearCompare;
                }

                return parseLocalDate(expense1).compareTo(parseLocalDate(expense2));
            } catch (FinbroException e) {
                logger.log(Level.WARNING, "Unable to parse date for expense: {0}", expense1.date());
                return 0;
            }
        });

        logger.log(Level.INFO, "Expenses successfully sorted by month");
        return sorted;
    }

    //@@author AK47ofCode
    /**
     * Sorts expenses by year, i.e. in true chronological order, in this case.
     *
     * @param expenses The list of expenses to sort.
     * @return A list of expenses sorted by year (in proper chronological order).
     */
    private static List<Expense> sortByYear(List<Expense> expenses) {
        logger.log(Level.INFO, "Sorting expenses by year");
        List<Expense> sorted = new ArrayList<>(expenses);

        sorted.sort((expense1, expense2) -> {
            LocalDate month1 = LocalDate.parse(expense1.date(), DATE_FORMATTER);
            LocalDate month2 = LocalDate.parse(expense2.date(), DATE_FORMATTER);
            return month1.compareTo(month2);
        });

        logger.log(Level.INFO, "Expenses successfully sorted by year");
        return sorted;
    }

    //@@author AK47ofCode
    /**
     * Sorts expenses by category in alphabetical order.
     *
     * @param expenses The list of expenses to sort.
     * @return A list of expenses sorted by category.
     */
    private static List<Expense> sortByCategory(List<Expense> expenses) {
        logger.log(Level.INFO, "Sorting expenses by category");
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparing(Expense::category).thenComparingDouble(Expense::amount));
        logger.log(Level.INFO, "Expenses successfully sorted by category");
        return sorted;
    }

    //@@author AK47ofCode
    /**
     * Sorts expenses by amount in descending order (highest to lowest).
     *
     * @param expenses The list of expenses to sort.
     * @return A list of expenses sorted by amount.
     */
    private static List<Expense> sortByAmount(List<Expense> expenses) {
        logger.log(Level.INFO, "Sorting expenses by amount");
        List<Expense> sorted = new ArrayList<>(expenses);
        sorted.sort(Comparator.comparingDouble(Expense::amount).reversed());
        logger.log(Level.INFO, "Expenses successfully sorted by amount");
        return sorted;
    }

    //@@author AK47ofCode
    /**
     * Parses a date string into a YearMonth object.
     *
     * @param expense The expense object containing the date string.
     * @return A YearMonth object representing the month of the expense.
     * @throws FinbroException if the date format is invalid.
     */
    private static YearMonth parseYearMonth(Expense expense) throws FinbroException {
        return ExpenseList.parseYearMonth(expense);
    }

    private static LocalDate parseLocalDate(Expense expense) throws FinbroException {
        try {
            return LocalDate.parse(expense.date(), DATE_FORMATTER);
        } catch (DateTimeParseException ex) {
            throw new FinbroException("Corrupted expense: Invalid date format");
        }
    }

    //@@author AK47ofCode
    /**
     * Validates if a sort type is valid.
     *
     * @param sortType The sort type to validate.
     * @return true if the sort type is valid, false otherwise.
     */
    public static boolean isValidSortType(String sortType) {
        assert sortType != null: "Sort type should not be null";
        return sortType.equalsIgnoreCase(SORT_YEAR) ||
               sortType.equalsIgnoreCase(SORT_MONTH) ||
               sortType.equalsIgnoreCase(SORT_CATEGORY) ||
               sortType.equalsIgnoreCase(SORT_AMOUNT);
    }
}
