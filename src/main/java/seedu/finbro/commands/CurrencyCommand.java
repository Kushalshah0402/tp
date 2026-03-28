package seedu.finbro.commands;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.CurrencyRateTable;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;


public class CurrencyCommand extends Command {
    //@@author WangZX2001
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        if (expenseList.isEmpty()) {
            throw new FinbroException("No expenses available.");
        }

        ui.showEnterSourceCurrencyPrompt();
        String fromCurrency = ui.readCommand().trim().toUpperCase();

        ui.showEnterTargetCurrencyPrompt();
        String toCurrency = ui.readCommand().trim().toUpperCase();

        if (CurrencyRateTable.isUnsupportedCurrency(fromCurrency)
                || CurrencyRateTable.isUnsupportedCurrency(toCurrency)) {
            throw new FinbroException("Unsupported currency. Supported currencies: "
                    + CurrencyRateTable.getSupportedCurrencies());
        }

        ui.showAllExpenses(expenseList.getAll());

        ui.showChooseExpenseEntryPrompt();
        String input = ui.readCommand().trim();

        int index;
        try {
            index = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new FinbroException("Invalid entry number.");
        }

        if (index < 1 || index > expenseList.size()) {
            throw new FinbroException("Entry out of range.");
        }

        Expense expense = expenseList.get(index - 1);

        double convertedAmount;
        try {
            convertedAmount = CurrencyRateTable.convert(
                    expense.amount(), fromCurrency, toCurrency);
        } catch (IllegalArgumentException e) {
            throw new FinbroException(e.getMessage());
        }

        ui.showCurrencyConversionResult(index, expense.amount(),
                fromCurrency, toCurrency, convertedAmount);
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
