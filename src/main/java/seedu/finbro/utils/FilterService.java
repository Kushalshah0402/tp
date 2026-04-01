package seedu.finbro.utils;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.Expense;
import seedu.finbro.finances.ExpenseList;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

//@@author AK47ofCode
/**
 * Service class to filter expenses.
 */
public class FilterService {
	private static final Logger logger = Logger.getLogger(FilterService.class.getName());

	//@@author AK47ofCode
	/**
	 * Filters expenses to only those that fall in the given month.
	 * The month value is case-insensitive and must be a full month name.
	 *
	 * @param expenses the expenses to filter.
	 * @param monthText the month name to match, e.g. "January".
	 * @return a new list containing only matching expenses.
	 * @throws FinbroException if the month text is invalid or an expense date is malformed.
	 */
	public static List<Expense> filterExpensesByMonth(List<Expense> expenses, String monthText)
			throws FinbroException {
		logger.log(Level.INFO, "Filtering expenses by month: {0}", monthText);

		Month targetMonth = parseMonthFilter(monthText);
		DateTimeFormatter expenseFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH);
		List<Expense> filtered = new ArrayList<>();

		for (Expense expense : expenses) {
			try {
				LocalDate date = LocalDate.parse(expense.date(), expenseFormatter);
				if (date.getMonth() == targetMonth) {
					filtered.add(expense);
				}
			} catch (DateTimeParseException e) {
				logger.log(Level.WARNING, "Unable to parse date for expense: {0}", expense.date());
				throw new FinbroException("Corrupted expense: Invalid date format");
			}
		}

		return filtered;
	}

	//@@author AK47ofCode
	/**
	 * Parses a month name into a Month enum value.
	 */
	private static Month parseMonthFilter(String monthText) throws FinbroException {
		DateTimeFormatter monthFormatter = new DateTimeFormatterBuilder()
				.parseCaseInsensitive()
				.appendPattern("MMMM")
				.toFormatter(Locale.ENGLISH);

		try {
			return Month.from(monthFormatter.parse(monthText));
		} catch (DateTimeParseException e) {
			throw new FinbroException("Invalid month for -filter: " + monthText
					+ "\nUse full month name, e.g. January.");
		}
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

    //@@author AK47ofCode
    /**
     * Validates if a filter type is valid.
     *
     * @param filterType The filter type to validate.
     * @return true if the filter type is valid, false otherwise.
     */
    public static boolean isValidFilterType(String filterType) {
        return filterType.equalsIgnoreCase(FILTER_MONTH) ||
               filterType.equalsIgnoreCase(FILTER_CATEGORY) ||
               filterType.equalsIgnoreCase(FILTER_AMOUNT);
    }
}
