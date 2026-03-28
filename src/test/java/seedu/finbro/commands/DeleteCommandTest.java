package seedu.finbro.commands;

import org.junit.jupiter.api.Test;
import seedu.finbro.exception.FinbroException;
import seedu.finbro.parser.Parser;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeleteCommandTest {
    //@@author zihaoalt
    @Test
    public void execute_strictModeValidInput_expenseDeleted() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-27").execute(expenses, ui, storage);
        Parser.parse("add 8.00 food 2026-03-28").execute(expenses, ui, storage);

        DeleteCommand command = new DeleteCommand("food 1");
        command.execute(expenses, ui, storage);

        assertEquals(1, expenses.size());
        assertEquals(1, expenses.getCategoryExpenses("food").size());
    }
    //@@author zihaoalt
    @Test
    public void execute_strictModeMissingIndex_exceptionThrown() {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("food");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Usage: delete <category> <number>", exception.getMessage());
    }
    //@@author zihaoalt
    @Test
    public void execute_strictModeInvalidIndex_exceptionThrown() {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("food abc");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Expense number must be a number.", exception.getMessage());
    }
    //@@author zihaoalt
    @Test
    public void execute_strictModeExpenseDoesNotExist_exceptionThrown() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-28").execute(expenses, ui, storage);

        assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("food 2");
            command.execute(expenses, ui, storage);
        });

        assertEquals(1, expenses.size());
    }
    //@@author zihaoalt
    @Test
    public void execute_walkthroughValidFlow_expenseDeleted() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-28").execute(expenses, ui, storage);
        Parser.parse("add 8.00 food 2026-03-28").execute(expenses, ui, storage);
        Parser.parse("add 3.20 transport 2026-03-28").execute(expenses, ui, storage);

        ui.setInputs("", "-l", "food", "-l", "abc", "0", "2", "yes");

        DeleteCommand command = new DeleteCommand("");
        command.execute(expenses, ui, storage);

        assertEquals(2, expenses.size());
        assertEquals(1, expenses.getCategoryExpenses("food").size());
        assertEquals(3, ui.inlineErrorCount);
        assertEquals(1, ui.allCategoryNamesShownCount);
        assertEquals(1, ui.categoryExpensesShownCount);
        assertEquals(1, ui.confirmExpenseShownCount);
        assertEquals(1, ui.expenseRemovedShownCount);
        assertEquals(2, ui.remainingExpenseCount);
        assertEquals("Index out of bounds.", ui.lastInlineError);
    }
    //@@author zihaoalt
    @Test
    public void execute_walkthroughCancelDelete_expenseNotDeleted() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-28").execute(expenses, ui, storage);

        ui.setInputs("food", "1", "no");

        DeleteCommand command = new DeleteCommand("");
        command.execute(expenses, ui, storage);

        assertEquals(1, expenses.size());
        assertEquals(1, ui.cancelDeleteShownCount);
        assertEquals(0, ui.expenseRemovedShownCount);
    }
    //@@author zihaoalt
    @Test
    public void execute_walkthroughNullCategoryInput_exceptionThrown() {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        ui.setInputs((String) null);

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Failed to read category input.", exception.getMessage());
    }
    //@@author zihaoalt
    @Test
    public void execute_walkthroughUnknownCategoryThenNullIndexInput_exceptionThrown() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-28").execute(expenses, ui, storage);

        ui.setInputs("travel", "food", (String) null);

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Failed to read index input.", exception.getMessage());
        assertEquals(1, ui.inlineErrorCount);
        assertEquals("Category travel does not exist.", ui.lastInlineError);
    }
    //@@author zihaoalt
    @Test
    public void execute_walkthroughNullConfirmationInput_exceptionThrown() throws FinbroException {
        ExpenseList expenses = new ExpenseList();
        TestUi ui = new TestUi();
        Storage storage = new Storage("./data/test-delete-finbro.txt");

        Parser.parse("add 12.50 food 2026-03-15").execute(expenses, ui, storage);

        ui.setInputs("food", "1", (String) null);

        FinbroException exception = assertThrows(FinbroException.class, () -> {
            DeleteCommand command = new DeleteCommand("");
            command.execute(expenses, ui, storage);
        });

        assertEquals("Failed to read confirmation input.", exception.getMessage());
        assertEquals(1, ui.confirmExpenseShownCount);
    }
    //@@author zihaoalt
    private static class TestUi extends Ui {
        private String[] inputs;
        private int index = 0;

        private int inlineErrorCount = 0;
        private String lastInlineError = "";

        private int allCategoryNamesShownCount = 0;
        private int categoryExpensesShownCount = 0;
        private int confirmExpenseShownCount = 0;
        private int expenseRemovedShownCount = 0;
        private int cancelDeleteShownCount = 0;
        private int remainingExpenseCount = -1;

        public void setInputs(String... inputs) {
            this.inputs = inputs;
            this.index = 0;
        }

        @Override
        public String readCommand() {
            return inputs[index++];
        }

        @Override
        public void showEnterCategoryOptionPrompt() { }

        @Override
        public void showEnterIndexPrompt() { }

        @Override
        public void showInlineError(String message) {
            inlineErrorCount++;
            lastInlineError = message;
        }

        @Override
        public void showAllCategoryNames(List<String> categoryNames) {
            allCategoryNamesShownCount++;
        }

        @Override
        public void showCategoryExpenses(String category, List<Expense> categoryList) {
            categoryExpensesShownCount++;
        }

        @Override
        public void showConfirmExpense(Expense expense) {
            confirmExpenseShownCount++;
        }

        @Override
        public void showExpenseRemoved(Expense expense, int remainingExpenseCount) {
            expenseRemovedShownCount++;
            this.remainingExpenseCount = remainingExpenseCount;
        }

        @Override
        public void showCancelDeleteMessage() {
            cancelDeleteShownCount++;
        }
    }
}
