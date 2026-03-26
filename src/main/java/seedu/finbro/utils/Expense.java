package seedu.finbro.utils;

public class Expense {
    private final double amount;
    private final String category;
    private final String date;

    //@@author Kushalshah0402
    public Expense(double amount, String category, String date) {
        assert category != null : "Category cannot be null";
        assert date != null : "Date cannot be null";
        this.amount = amount;
        this.category = category;
        this.date = date;
    }
    //@@author Kushalshah0402
    public double getAmount() {
        return amount;
    }

    //@@author Kushalshah0402
    public String getCategory() {
        return category;
    }

    //@@author Kushalshah0402
    public String getDate() {
        return date;
    }

    //@@author Kushalshah0402
    @Override
    public String toString() {
        return " Amount: $" + String.format("%.2f", amount) + "\n   Category: " + category + "\n   Date: " + date;
    }
}
