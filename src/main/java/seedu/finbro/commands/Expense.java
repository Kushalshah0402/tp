package seedu.finbro.commands;

public class Expense {
    private double amount;
    private String category;
    private String date;

    public Expense(double amount, String category, String date) {
        assert category != null : "Category cannot be null";
        assert date != null : "Date cannot be null";
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return " Amount: $" + String.format("%.2f", amount) + "\n   Category: " + category + "\n   Date: " + date;
    }
}
