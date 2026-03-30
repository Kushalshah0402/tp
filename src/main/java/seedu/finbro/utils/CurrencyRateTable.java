package seedu.finbro.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//@@author WangZX2001
public class CurrencyRateTable {

    private static final Logger logger = Logger.getLogger(CurrencyRateTable.class.getName());
    private static final String BASE_CURRENCY = "SGD";
    private static final Map<String, Double> sgdToRate = new HashMap<>();

    static {
        sgdToRate.put("USD", 0.74);   // US Dollar
        sgdToRate.put("EUR", 0.68);   // Euro
        sgdToRate.put("GBP", 0.58);   // British Pound
        sgdToRate.put("JPY", 111.20); // Japanese Yen
        sgdToRate.put("CNY", 5.36);   // Chinese Yuan
        sgdToRate.put("AUD", 1.00);   // Australian Dollar
        sgdToRate.put("CAD", 0.99);   // Canadian Dollar
        sgdToRate.put("MYR", 3.47);   // Malaysian Ringgit
        sgdToRate.put("HKD", 5.78);   // Hong Kong Dollar
        sgdToRate.put("KRW", 990.00); // South Korean Won

        logger.info("CurrencyRateTable initialized with supported currencies: " + sgdToRate.keySet());
    }

    public static boolean isUnsupportedCurrency(String currency) {
        String code = currency.toUpperCase();

        boolean unsupported =  !code.equals(BASE_CURRENCY) && !sgdToRate.containsKey(code);

        if (unsupported) {
            logger.warning("Unsupported currency checked: " + code);
        }
        return unsupported;
    }

    public static double convert(double amount, String fromCurrency, String toCurrency) {
        String from = fromCurrency.toUpperCase();
        String to = toCurrency.toUpperCase();

        logger.fine("Attempting conversion: " + amount + " " + from + " -> " + to);

        if (from.equals(to)) {
            return amount;
        }

        if (isUnsupportedCurrency(from) || isUnsupportedCurrency(to)) {
            logger.severe("Conversion failed due to unsupported currency: " + from + " or " + to);
            throw new IllegalArgumentException("Unsupported currency.");
        }

        if (from.equals(BASE_CURRENCY)) {
            double result = amount * sgdToRate.get(to);
            logger.fine("Converted from SGD: " + result);
            return result;
        }

        if (to.equals(BASE_CURRENCY)) {
            double result = amount / sgdToRate.get(from);
            logger.fine("Converted to SGD: " + result);
            return result;
        }

        double amountInSgd = amount / sgdToRate.get(from);
        double result = amountInSgd * sgdToRate.get(to);

        logger.fine("Cross conversion result: " + result);

        return result;
    }

    public static String getSupportedCurrencies() {
        return BASE_CURRENCY + ", " + String.join(", ", sgdToRate.keySet());
    }
}
