package seedu.finbro.commands;

import java.time.YearMonth;
import java.util.Collections;
import java.util.IllegalFormatException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.ExpenseList;

public class VisualCommand extends Command {
    public static final int MAX_BAR_LENGTH = 20;

    private static final Logger logger = Logger.getLogger(VisualCommand.class.getName());

    String arg;
    String output = "";

    public VisualCommand(String arg) {
        this.arg = arg;
    }

    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        logger.log(Level.INFO, "Attempting to create visualisation");

        // TreeMap sorts by key (YearMonth)
        Map<YearMonth, Double> monthlyTotals = new TreeMap<>(expenses.getMonthlyExpenses());

        if (monthlyTotals.isEmpty()) {
            logger.log(Level.WARNING, "Visualisation cancelled: no monthly expenses found");
            throw new  FinbroException("Error: No expenses found");
        }

        double max = Collections.max(monthlyTotals.values());
        for (YearMonth month: monthlyTotals.keySet()) {
            double amount =  monthlyTotals.get(month);
            assert amount >= 0;

            int barLength = (int) ((amount / max) * MAX_BAR_LENGTH);
            String bar = createBar(barLength);
            String label = createLabel(month);
            createRow(label, bar, amount);
        }

        logger.log(Level.INFO, "Visualisation created");
        ui.showVisual(output);
    }

    public static String createBar(int barLength) {
        assert barLength >= 0;
        return "█".repeat(barLength);
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
            logger.log(Level.SEVERE, "Invalid row label/bar strings");
            throw new FinbroException("Error: label or bar is null");
        }

        if (!output.isBlank()) {
            output += "\n";
        }

        try {
            output += String.format("%-8s | %-20s $%.2f", label, bar, amount);
        } catch (IllegalFormatException e) {
            logger.log(Level.SEVERE, "String formatting error");
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
