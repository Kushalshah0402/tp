package seedu.finbro.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Limit {
    private static double limit = 0;

    private static final Logger logger = Logger.getLogger(Limit.class.getName());

    public static double getLimit() {
        return limit;
    }

    public static String toFileFormat() {
        return String.format("%.2f", Limit.getLimit()) + "\n";
    }

    public static void setLimit(double limit) {
        if (limit >= 0) {
            logger.log(Level.INFO, "Setting limit to {0}", limit);
            Limit.limit = limit;
        } else {
            logger.log(Level.WARNING, "Attempted to set negative limit {0}", limit);
        }
    }
}
