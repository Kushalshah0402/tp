package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;

public class CurrencyCommandTest {

    private static final String TEST_FILE_PATH = "./data/test-finbro.txt";
    //@@author WangZX2001
    @Test
    void execute_validInput_success() {
        TestUi ui = new TestUi();
        ui.setInputs("SGD", "USD", "1");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));
    }
    //@@author WangZX2001
    @Test
    void execute_sameCurrency_success() {
        TestUi ui = new TestUi();
        ui.setInputs("SGD", "SGD", "1");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(50.0, "transport", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));
    }
    //@@author WangZX2001
    @Test
    void execute_lowercaseCurrency_success() {
        TestUi ui = new TestUi();
        ui.setInputs("sgd", "usd", "1");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(80.0, "shopping", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));
    }
    //@@author WangZX2001
    @Test
    void execute_unsupportedSourceCurrency_throwsException() {
        TestUi ui = new TestUi();
        ui.setInputs("ABC", "USD", "1");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));

        assertTrue(e.getMessage().contains("Unsupported currency"));
    }
    //@@author WangZX2001
    @Test
    void execute_unsupportedTargetCurrency_throwsException() {
        TestUi ui = new TestUi();
        ui.setInputs("SGD", "XYZ", "1");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));

        assertTrue(e.getMessage().contains("Unsupported currency"));
    }
    //@@author WangZX2001
    @Test
    void execute_nonNumericEntry_throwsException() {
        TestUi ui = new TestUi();
        ui.setInputs("SGD", "USD", "abc");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));

        assertEquals("Invalid entry number.", e.getMessage());
    }
    //@@author WangZX2001
    @Test
    void execute_entryOutOfRange_throwsException() {
        TestUi ui = new TestUi();
        ui.setInputs("SGD", "USD", "2");

        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, new Storage(TEST_FILE_PATH)));

        assertEquals("Entry out of range.", e.getMessage());
    }
    //@@author WangZX2001
    @Test
    void getHelpMessage_containsCurrencyKeyword() {
        CurrencyCommand command = new CurrencyCommand();
        assertTrue(command.getHelpMessage().toLowerCase().contains("currency"));
    }
    //@@author WangZX2001
    private static class TestUi extends Ui {
        private String[] inputs;
        private int index = 0;

        void setInputs(String... inputs) {
            this.inputs = inputs;
            this.index = 0;
        }

        @Override
        public String readCommand() {
            return inputs[index++];
        }
    }
}
