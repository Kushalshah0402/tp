package seedu.finbro.commands;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.finances.ExpenseList;
import seedu.finbro.finances.Limit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditLimitCommandTest {
    private static final String TEST_FILE_PATH = "./data/test-finbro.txt";

    //@@author WangZX2001
    @Test
    public void execute_replaceLimit_success() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("3", "800", "yes");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();
        command.execute(expenses, ui, storage);

        assertEquals(800.0, Limit.getLimit());
    }

    //@@author WangZX2001
    @Test
    public void execute_increaseLimit_success() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("1", "100", "Y");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();
        command.execute(expenses, ui, storage);

        assertEquals(600.0, Limit.getLimit());
    }

    //@@author WangZX2001
    @Test
    public void execute_decreaseLimit_success() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("2", "200", "yes");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();
        command.execute(expenses, ui, storage);

        assertEquals(300.0, Limit.getLimit());
    }

    //@@author WangZX2001
    @Test
    public void execute_invalidChoice_retriesSuccessfully() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("4", "1", "100", "yes");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();
        command.execute(expenses, ui, storage);

        assertEquals(600.0, Limit.getLimit());
    }

    //@@author WangZX2001
    @Test
    public void execute_replaceLimit_invalidAmount() {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("3", "abc");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> command.execute(expenses, ui, storage));

        assertEquals("Monthly spending limit must be a number", exception.getMessage());
        assertEquals(500.0, Limit.getLimit());
    }

    // New tests to improve branch coverage

    //@@author WangZX2001
    @Test
    public void execute_choiceWithWhitespace_trimmed() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs(" 1 ", "50", "yes");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();
        command.execute(expenses, ui, storage);

        assertEquals(550.0, Limit.getLimit());
    }

    //@@author WangZX2001
    @Test
    public void execute_decreaseBelowZero_exceptionThrown() {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage(TEST_FILE_PATH);

        ui.setInputs("2", "600");
        Limit.setLimit(500.0);

        EditLimitCommand command = new EditLimitCommand();

        FinbroException exception = assertThrows(FinbroException.class,
                () -> command.execute(expenses, ui, storage));

        assertEquals("Monthly spending limit must be at least $0", exception.getMessage());
        assertEquals(500.0, Limit.getLimit());
    }
    //@@author WangZX2001
    @Test
    public void getHelpMessage_returnsExpectedMessage() {
        EditLimitCommand command = new EditLimitCommand();
        String helpMessage = command.getHelpMessage();

        assertTrue(helpMessage.contains("Edits the monthly spending limit."));
        assertTrue(helpMessage.contains("Format: edit limit"));
        assertTrue(helpMessage.contains("follow the prompts to increase, decrease, or replace the limit"));
        assertTrue(helpMessage.contains("Do not include any parameters after entering 'edit limit'"));
    }
    //@@author WangZX2001
    private static class TestUi extends Ui {
        private String[] inputs;
        private int index = 0;

        public void setInputs(String... inputs) {
            this.inputs = inputs;
            this.index = 0;
        }

        @Override
        public String readCommand() {
            return inputs[index++];
        }

        @Override
        public void showLimitEditMenu(double currentLimit) { }

        @Override
        public void showEnterAmountPrompt(String action) { }

        @Override
        public void showLimit() { }
    }
}
