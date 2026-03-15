package seedu.finbro;

import seedu.finbro.commands.Expense;
import seedu.finbro.commands.Limit;
import seedu.finbro.exception.FinbroException;

import java.util.ArrayList;
import java.util.List;

public class ExpenseList {
    private List<Expense> expenses;
    public ExpenseList() {
        expenses = new ArrayList<>();
    }

    public ExpenseList(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public void add(Expense e) {
        expenses.add(e);
    }

    public int size() {
        return expenses.size();
    }

    public List<Expense> getAll() {
        return expenses;
    }

    public double getTotalExpenditure() {
        double total = 0;
        for (Expense e : expenses) {
            total += e.getAmount();
        }
        return total;
    }

    public double getRemainingExpenditure() {
        double total = getTotalExpenditure();
        double limit = Limit.getLimit();
        return limit - total;
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

        expenses.remove(targetExpense);
        return targetExpense;
    }
}
