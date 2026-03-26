package seedu.finbro.utils;

import seedu.finbro.exception.FinbroException;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private final List<Expense> expenses;
    private double total = 0;

    public ExpenseList() {
        expenses = new ArrayList<>();
    }

    /**
     * @author Kushalshah0402 AK47ofCode
     */
    public ExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
    }
    /**
     * @author Kushalshah0402 AK47ofCode
     */
    public void add(Expense e) {
        assert e != null : "Expense to add should not be null";
        expenses.add(e);
        total += e.getAmount();
    }
    /**
     * @author Kushalshah0402
     */
    public int size() {
        return expenses.size();
    }
    /**
     * @author Kushalshah0402
     */
    public List<Expense> getAll() {
        return expenses;
    }
    /**
     * @author AK47ofCode
     */
    public double getTotalExpenditure() {
        return total;
    }
    /**
     * @author AK47ofCode
     */
    public double getRemainingExpenditure() {
        return Limit.getLimit() - total;
    }
    /**
     * @author zihaoalt
     */
    public List<Expense> getCategoryExpenses(String category) {
        List<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equals(category)) {
                results.add(e);
            }
        }
        return results;
    }
    /**
     * @author zihaoalt
     */
    public Expense removeByCategoryIndex(String category, int number) throws FinbroException {
        if (number <= 0) {
            throw new FinbroException("Expense number must be positive");
        }

        int count = 0;
        Expense targetExpense = null;
        for (Expense expense : expenses) {
            if (expense.getCategory().equals(category)) {
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

        total -= targetExpense.getAmount();
        expenses.remove(targetExpense);
        assert !expenses.contains(targetExpense) : "Removed expense should no longer be in list";
        return targetExpense;
    }
}
