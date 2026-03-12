package seedu.finbro.commands;

import seedu.finbro.Ui;

public class Limit {
    private static double limit = 0;
    private static double spent = 0;

    public static double getLimit() {
        return limit;
    }

    public static double getSpent() {
        return spent;
    }

    public static String toFileFormat() {
        return String.format("%.2f", Limit.getLimit()) + "\n";
    }

    public static void initLimit(double limit) {
        Limit.limit = limit;
    }

    public static void setLimit(double limit, Ui ui) {
        ui.showChangeLimitWarning(limit);
        String input = ui.readCommand();
        if (input.equals("yes")) {
            Limit.limit = limit;
        } else if (input.equals("no")) {
            ui.showCancelChangeLimitMessage();
        } else {
            ui.showCancelChangeLimitMessage();
        }
    }
}