//package seedu.finbro.utils;
//
//import seedu.finbro.exception.FinbroException;
//
//import java.time.YearMonth;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
////@@author AK47ofCode
///**
// * Service class to handle filtering and sorting of expenses.
// */
//public class FilterService {
//    private static final Logger logger = Logger.getLogger(FilterService.class.getName());
//    private static final String FILTER_MONTH = "month";
//    private static final String FILTER_CATEGORY = "category";
//    private static final String FILTER_AMOUNT = "amount";
//
//    //@@author AK47ofCode
//    /**
//     * Filters and sorts a list of expenses based on the specified filter type.
//     *
//     * @param expenses The list of expenses to filter.
//     * @param filterType The type of filter to apply (month, category, or amount).
//     * @return A sorted list of expenses.
//     * @throws FinbroException if the filter type is invalid.
//     */
//    public static List<Expense> filterExpenses(List<Expense> expenses, String filterType) throws FinbroException {
//        logger.log(Level.INFO, "Filtering expenses by: {0}", filterType);
//
//        return switch (filterType.toLowerCase()) {
//        case FILTER_MONTH -> sortByMonth(expenses);
//        case FILTER_CATEGORY -> sortByCategory(expenses);
//        case FILTER_AMOUNT -> sortByAmount(expenses);
//        default -> throw new FinbroException("Invalid filter type. Supported filters: month, category, amount");
//        };
//    }
//
//    //@@author AK47ofCode
//    /**
//     * Sorts expenses by month in chronological order.
//     *
//     * @param expenses The list of expenses to sort.
//     * @return A list of expenses sorted by month.
//     */
//    private static List<Expense> sortByMonth(List<Expense> expenses) {
//        logger.log(Level.INFO, "Sorting expenses by month");
//        List<Expense> sorted = new ArrayList<>(expenses);
//
//        sorted.sort((expense1, expense2) -> {
//            try {
//                YearMonth month1 = parseYearMonth(expense1);
//                YearMonth month2 = parseYearMonth(expense2);
//                return month1.compareTo(month2);
//            } catch (FinbroException e) {
//                logger.log(Level.WARNING, "Unable to parse date for expense: {0}", expense1.date());
//                return 0;
//            }
//        });
//
//        logger.log(Level.INFO, "Expenses successfully sorted by month");
//        return sorted;
//    }
//
//    //@@author AK47ofCode
//    /**
//     * Sorts expenses by category in alphabetical order.
//     *
//     * @param expenses The list of expenses to sort.
//     * @return A list of expenses sorted by category.
//     */
//    private static List<Expense> sortByCategory(List<Expense> expenses) {
//        logger.log(Level.INFO, "Sorting expenses by category");
//        List<Expense> sorted = new ArrayList<>(expenses);
//        sorted.sort(Comparator.comparing(Expense::category).thenComparingDouble(Expense::amount));
//        logger.log(Level.INFO, "Expenses successfully sorted by category");
//        return sorted;
//    }
//
//    //@@author AK47ofCode
//    /**
//     * Sorts expenses by amount in descending order (highest to lowest).
//     *
//     * @param expenses The list of expenses to sort.
//     * @return A list of expenses sorted by amount.
//     */
//    private static List<Expense> sortByAmount(List<Expense> expenses) {
//        logger.log(Level.INFO, "Sorting expenses by amount");
//        List<Expense> sorted = new ArrayList<>(expenses);
//        sorted.sort(Comparator.comparingDouble(Expense::amount).reversed());
//        logger.log(Level.INFO, "Expenses successfully sorted by amount");
//        return sorted;
//    }
//
//    //@@author AK47ofCode
//    /**
//     * Parses a date string into a YearMonth object.
//     *
//     * @param expense The expense object containing the date string.
//     * @return A YearMonth object representing the month of the expense.
//     * @throws FinbroException if the date format is invalid.
//     */
//    private static YearMonth parseYearMonth(Expense expense) throws FinbroException {
//        return ExpenseList.getYearMonth(expense);
//    }
//
//    //@@author AK47ofCode
//    /**
//     * Validates if a filter type is valid.
//     *
//     * @param filterType The filter type to validate.
//     * @return true if the filter type is valid, false otherwise.
//     */
//    public static boolean isValidFilterType(String filterType) {
//        return filterType.equalsIgnoreCase(FILTER_MONTH) ||
//               filterType.equalsIgnoreCase(FILTER_CATEGORY) ||
//               filterType.equalsIgnoreCase(FILTER_AMOUNT);
//    }
//}

