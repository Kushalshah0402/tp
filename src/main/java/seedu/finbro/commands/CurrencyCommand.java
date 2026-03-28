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
        String fromCurrency = ui.readLine().trim().toUpperCase();

        ui.showEnterTargetCurrencyPrompt();
        String toCurrency = ui.readLine().trim().toUpperCase();

        if (CurrencyRateTable.isSupportedCurrency(fromCurrency)
                || CurrencyRateTable.isSupportedCurrency(toCurrency)) {
            throw new FinbroException("Unsupported currency. Supported currencies: "
                    + CurrencyRateTable.getSupportedCurrencies());
        }

        ui.showAllExpenses(expenseList.getAll());

        ui.showChooseExpenseEntryPrompt();
        String input = ui.readLine().trim();

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
        return "currency: convert an expense using locally stored exchange rates.";
    }
}