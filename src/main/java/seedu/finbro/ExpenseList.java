package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;
import seedu.finbro.exception.FinbroException;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private List<Expense> expenses;
    private double total = 0;

    public ExpenseList() {
        expenses = new ArrayList<>();
        Limit.setSpent(total);
    }

    public ExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        Limit.setSpent(total);
    }

    public void add(Expense e) {
        assert e != null : "Expense to add should not be null";
        expenses.add(e);
        total += e.getAmount();
        Limit.setSpent(total);
    }

    public int size() {
        return expenses.size();
    }

    public List<Expense> getAll() {
        return expenses;
    }

    public double getTotalExpenditure() {
        return total;
    }

    public double getRemainingExpenditure() {
        return Limit.getLimit() - total;
    }

    public List<Expense> getCategoryExpenses(String category) {
        List<Expense> results = new ArrayList<>();
        for (Expense e : expenses) {
            if (e.getCategory().equals(category)) {
                results.add(e);
            }
        }
        return results;
    }

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
        Limit.setSpent(total);
        expenses.remove(targetExpense);
        assert !expenses.contains(targetExpense) : "Removed expense should no longer be in list";
        return targetExpense;
    }
}
