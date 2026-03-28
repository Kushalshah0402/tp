package seedu.finbro.commands;

import java.time.YearMonth;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.TreeMap;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.ExpenseList;

public class VisualCommand extends Command {
    public static final int MAX_BAR_LENGTH = 20;

    String arg;
    String output = "";

    public VisualCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        // TreeMap sorts by key (YearMonth)
        Map<YearMonth, Double> monthlyTotals = new TreeMap<>(expenses.getMonthlyExpenses());

        if (monthlyTotals.isEmpty()) {
            throw new  FinbroException("Error: No expenses found");
        }

        double max = Collections.max(monthlyTotals.values());
        for (YearMonth month: monthlyTotals.keySet()) {
            double amount =  monthlyTotals.get(month);
            int barLength = (int) ((amount / max) * MAX_BAR_LENGTH);
            String bar = createBar(barLength);
            String label = createLabel(month);
            createRow(label, bar, amount);
        }

        ui.showVisual(output);
    }

    public static String createBar(int barLength) {
        return "█".repeat(Math.max(0, barLength));
    }

    public static String createLabel(YearMonth yearMonth) {
        String month = yearMonth.getMonth().getDisplayName(
                java.time.format.TextStyle.SHORT,
                java.util.Locale.ENGLISH);
        String year = String.valueOf(yearMonth.getYear());

        return month + " " + year;
    }

    public void createRow(String label, String bar, double amount) throws FinbroException {
        if (label == null || bar == null) {
            throw new FinbroException("Error: label or bar is null");
        }

        if (!output.isBlank()) {
            output += "\n";
        }

        try {
            output += String.format("%-8s | %-20s $%.2f", label, bar, amount);
        } catch (IllegalFormatException e) {
            throw new FinbroException(e.getMessage());
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Visualise your monthly spendings.
                Format: visual
                Use: Displays a bar chart that shows your spendings across different months.""";
    }
}
