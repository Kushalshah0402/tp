package seedu.finbro.finances;

import seedu.finbro.exception.FinbroException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExpenseList {
    private static final Logger logger = Logger.getLogger(ExpenseList.class.getName());

    private final List<Expense> expenses;
    private double total = 0;

    public ExpenseList() {
        expenses = new ArrayList<>();
    }

    //@@author Kushalshah0402 AK47ofCode
    public ExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
        for (Expense expense : expenses) {
            total += expense.amount();
        }
    }
    //@@author Kushalshah0402 AK47ofCode
    public void add(Expense e) {
        assert e != null : "Expense to add should not be null";


        expenses.add(e);
        total += e.amount();
    }

    //@@author WangZX2001
    public boolean isEmpty() {
        return expenses.isEmpty();
    }

    //@@author WangZX2001
    public Expense get(int index) {
        return expenses.get(index);
    }

    //@@author Kushalshah0402
    public int size() {
        return expenses.size();
    }

    //@@author Kushalshah0402
    public List<Expense> getAll() {
        return expenses;
    }

    //@@author AK47ofCode
    public double getTotalExpenditure() {
        return total;
    }

    //@@author AK47ofCode
    public double getRemainingExpenditure() {
        return Limit.getLimit() - total;
    }

    //@@author natmloclam
    public double getCurrentMonthTotalExpenditure() {
        YearMonth currentMonth = YearMonth.now();
        double currentMonthTotal = 0;

        for (Expense expense : expenses) {
            try {
                YearMonth expenseMonth = parseYearMonth(expense);
                if (expenseMonth.equals(currentMonth)) {
                    currentMonthTotal += expense.amount();
                }
            } catch (FinbroException e) {
                continue;
            }
        }
        return currentMonthTotal;
    }

    //@@author zihaoalt
    public List<Expense> getCategoryExpenses(String category) {
        List<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.category().equalsIgnoreCase(category)) {
                results.add(e);
            }
        }
        return results;
    }

    //@@author zihaoalt
    public Expense removeByCategoryIndex(String category, int number) throws FinbroException {

        boolean categoryExists = false;
        for (Expense expense : expenses) {
            if (expense.category().equalsIgnoreCase(category)) {
                categoryExists = true;
                break;
            }
        }
        if (!categoryExists) {
            throw new FinbroException("Expense category not found");
        }

        Expense targetExpense = getTargetExpense(category, number);

        expenses.remove(targetExpense);
        total -= targetExpense.amount();
        return targetExpense;
    }

    //@@author zihaoalt
    private Expense getTargetExpense(String category, int number) throws FinbroException {
        if (number <= 0) {
            throw new FinbroException("Expense number must be positive");
        }

        int count = 0;
        Expense targetExpense = null;
        for (Expense expense : expenses) {
            if (expense.category().equalsIgnoreCase(category)) {
                count++;
                if (count == number) {
                    targetExpense = expense;
                    break;
                }
            }
        }

        if (targetExpense == null) {
            throw new FinbroException("Expense number out of bounds");
        }
        return targetExpense;
    }


    public Map<YearMonth, Double> getMonthlyExpenses() throws FinbroException {
        logger.log(Level.INFO, "Sorting expenses by month...");

        Map<YearMonth, Double> monthlyTotals =  new HashMap<>();
        for (Expense expense : expenses) {
            try {
                logger.log(Level.INFO, "Expense: {0} | {1} | {2}",
                        new Object[]{expense.category(), expense.date(), expense.amount()});
                YearMonth month =  parseYearMonth(expense);
                monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + expense.amount());
            } catch (ClassCastException | NullPointerException |
                     IllegalArgumentException | UnsupportedOperationException e) {
                logger.log(Level.SEVERE, "Error: Unable to sort expense: " +
                        "{0} | {1} | {2}", new Object[]{expense.category(), expense.date(), expense.amount()});
                throw new FinbroException("Unable to add to Map");
            } catch (FinbroException e) {
                logger.log(Level.SEVERE, "Expense data corrupted, invalid date: {0}",  expense.date());
            }
        }
        logger.log(Level.INFO, "Expenses successfully sorted by month.");
        return monthlyTotals;
    }

    //@@author natmloclam AK47ofCode
    public static YearMonth parseYearMonth(Expense e) throws FinbroException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        LocalDate parsedDate;
        YearMonth yearMonth;
        try {
            parsedDate = LocalDate.parse(e.date(), formatter);
            yearMonth = YearMonth.from(parsedDate);
        } catch (DateTimeParseException ex) {
            throw new FinbroException("Corrupted expense: Invalid date format");
        }
        return yearMonth;
    }

    //@@author zihaoalt
    public List<String> getAllCategoryNames() {
        List<String> expenseNames = new ArrayList<>();
        for (Expense expense : expenses) {
            String category = expense.category();
            if (!expenseNames.contains(category)) {
                expenseNames.add(category);
            }
        }
        return expenseNames;
    }

    //@@author zihaoalt
    public Expense getExpenseByCategoryIndex(List<Expense> categoryList, int number) throws FinbroException {
        if (number <= 0) {
            throw new FinbroException("Expense number must be positive");
        }
        if (number > categoryList.size()) {
            throw new FinbroException("Expense number is out of bounds");
        }
        return categoryList.get(number - 1);
    }
}
