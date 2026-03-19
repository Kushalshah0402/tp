package seedu.finbro.parser;

import org.junit.jupiter.api.Test;
import seedu.finbro.commands.Command;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.ExpenseList;
import seedu.finbro.utils.Limit;
import seedu.finbro.exception.FinbroException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {

    @Test
    public void parse_addValidExpense_expenseAdded() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();

        Storage storage = new Storage("./data/test-finbro.txt");
        Command command = Parser.parse("add 12.50 food 2026-03-15");
        command.execute(expenses, ui, storage);
        assertEquals(1, expenses.size());
    }

    @Test
    public void parse_addInvalidAmount_exceptionThrown() {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();

        Storage storage = new Storage("./data/test-finbro.txt");

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            Command command = Parser.parse("add abc food 2026-03-15");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Amount must be a number.", exception.getMessage());
    }

    @Test
    public void parse_editLimitReplace() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-finbro.txt");

        // yes: confirm setting initial limit to 500
        // 3: choose replace
        // 800: new limit
        // yes: confirm changing limit to 800
        ui.setInputs("yes", "3", "800", "yes");

        Limit.setLimit(800.0);
        Parser.parse("edit limit");

        assertEquals(800.0, Limit.getLimit());
    }

    @Test
    public void parse_editLimitIncrease() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-finbro.txt");

        // yes: confirm setting initial limit
        // 1: choose increase
        // 100: increase amount
        // yes: confirm new limit
        ui.setInputs("1", "100", "yes");

        Limit.setLimit(500.0);
        Command command = Parser.parse("edit limit");
        command.execute(expenses, ui, storage);

        assertEquals(600.0, Limit.getLimit());
    }

    @Test
    public void parse_editLimitDecrease() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-finbro.txt");

        // yes: confirm setting initial limit
        // 2: choose decrease
        // 200: decrease amount
        // yes: confirm new limit
        ui.setInputs("2", "200", "yes");

        Limit.setLimit(500.0);
        Command command = Parser.parse("edit limit");
        command.execute(expenses, ui, storage);

        assertEquals(300.0, Limit.getLimit());
    }

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
