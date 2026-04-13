package seedu.finbro.commands;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.CurrencyRateTable;
import seedu.finbro.finances.Expense;
import seedu.finbro.finances.ExpenseList;

import java.util.logging.Logger;


public class CurrencyCommand extends Command {

    private static final Logger logger = Logger.getLogger(CurrencyCommand.class.getName());

    //@@author WangZX2001
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        assert expenseList != null : "ExpenseList should not be null";
        assert ui != null : "Ui should not be null";
        logger.info("Executing CurrencyCommand...");
        if (expenseList.isEmpty()) {
            logger.warning("No expenses available for conversion.");
            throw new FinbroException("No expenses available.");
        }

        ui.showEnterSourceCurrencyPrompt();
        String fromCurrency = ui.readCommand().trim().toUpperCase();

        ui.showEnterTargetCurrencyPrompt();
        String toCurrency = ui.readCommand().trim().toUpperCase();
        logger.info("User entered target currency: " + toCurrency);

        if (fromCurrency.equals(toCurrency)) {
            logger.info("No conversion needed: source and target currencies are the same (" + fromCurrency + ").");
            ui.showNoConversionNeeded(fromCurrency);
            return;
        }

        if (CurrencyRateTable.isUnsupportedCurrency(fromCurrency)
                || CurrencyRateTable.isUnsupportedCurrency(toCurrency)) {
            logger.warning("Unsupported currency entered: " + fromCurrency + " or " + toCurrency);
            throw new FinbroException("Unsupported currency. Supported currencies: "
                    + CurrencyRateTable.getSupportedCurrencies());
        }

        ui.showAllExpenses(expenseList.getAll());
        logger.info("Displayed all expenses to user.");

        ui.showChooseExpenseEntryPrompt();
        String input = ui.readCommand().trim();
        logger.info("User selected entry input: " + input);

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            logger.warning("Invalid entry number format: " + input);
            throw new FinbroException("Invalid entry number.");
        }

        if (index < 1 || index > expenseList.size()) {
            logger.warning("Entry index out of range: " + index);
            throw new FinbroException("Entry out of range.");
        }

        Expense expense = expenseList.get(index - 1);
        assert expense != null : "Selected expense should not be null";
        logger.info("Selected expense: " + expense);

        double convertedAmount;
        try {
            convertedAmount = CurrencyRateTable.convert(
                    expense.amount(), fromCurrency, toCurrency);
            logger.info(String.format("Conversion successful: %.2f %s -> %.2f %s",
                    expense.amount(), fromCurrency, convertedAmount, toCurrency));

        } catch (IllegalArgumentException e) {
            logger.severe("Conversion failed: " + e.getMessage());
            throw new FinbroException(e.getMessage());
        }

        ui.showCurrencyConversionResult(index, expense.amount(),
                fromCurrency, toCurrency, convertedAmount);
        logger.info("CurrencyCommand execution completed successfully.");
    }

    //@@author WangZX2001
    @Override
    public String getHelpMessage() {
        return """
                Converts an existing expense into another currency.
                Format: currency
                Use: Prompts you to enter the source and target currencies,
                     then select an expense entry to convert.
                Note: Supported currencies: SGD, USD, EUR, GBP, JPY, CNY, AUD, CAD, MYR, HKD, KRW
                      Uses locally stored exchange rates (no internet required).""";
    }
}
