package seedu.finbro.utils;

import seedu.finbro.exception.FinbroException;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseList {
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
    //@@author zihaoalt
    public List<Expense> getCategoryExpenses(String category) {
        List<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.category().equals(category)) {
                results.add(e);
            }
        }
        return results;
    }

    //@@author zihaoalt
    public Expense removeByCategoryIndex(String category, int number) throws FinbroException {
        if (number <= 0) {
            throw new FinbroException("Expense number must be positive");
        }

        int count = 0;
        Expense targetExpense = null;
        for (Expense expense : expenses) {
            if (expense.category().equals(category)) {
                count++;
            }
            if (count == number) {
                targetExpense = expense;
                break;
            }
        }

        if (count == 0) {
            throw new FinbroException("Expense category not found");
        }

        if (targetExpense == null) {
            throw new FinbroException("Expense number under the category is out of bounds");
        }

        total -= targetExpense.amount();
        expenses.remove(targetExpense);
        assert !expenses.contains(targetExpense) : "Removed expense should no longer be in list";
        return targetExpense;
    }

    public Map<YearMonth, Double> getMonthlyExpenses() throws FinbroException {
        Map<YearMonth, Double> monthlyTotals =  new HashMap<>();
        for (Expense expense : expenses) {
            try {
                YearMonth month =  parseMonth(expense);
                monthlyTotals.put(month, monthlyTotals.getOrDefault(month, 0.0) + expense.amount());
            } catch (ClassCastException | NullPointerException |
                     IllegalArgumentException | UnsupportedOperationException e) {
                throw new FinbroException("Unable to add to Map");
            }
        }
        return monthlyTotals;
    }

    public YearMonth parseMonth(Expense e) throws FinbroException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy");

        LocalDate parsedDate;
        YearMonth yearMonth;
        try {
            parsedDate = LocalDate.parse(e.date(), formatter);
            yearMonth = YearMonth.from(parsedDate);
        } catch (DateTimeParseException ex) {
            throw new FinbroException("Invalid date format");
        }
        return yearMonth;
    }
}
