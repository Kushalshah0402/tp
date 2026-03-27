package seedu.finbro.utils;

public record Expense(double amount, String category, String date) {
    //@@author Kushalshah0402
    public Expense {
        assert category != null : "Category cannot be null";
        assert date != null : "Date cannot be null";
    }

    //@@author Kushalshah0402
    @Override
    public String toString() {
        return " Amount: $" + String.format("%.2f", amount) + "\n   Category: " + category + "\n   Date: " + date;
    }
}
