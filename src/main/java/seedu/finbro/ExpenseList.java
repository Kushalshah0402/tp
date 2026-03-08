package seedu.finbro;

import seedu.finbro.commands.Expense;

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
}
