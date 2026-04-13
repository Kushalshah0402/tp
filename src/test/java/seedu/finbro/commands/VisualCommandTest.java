package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.finances.Expense;
import seedu.finbro.finances.ExpenseList;

class VisualCommandTest {

    @Test
    void createLabel_validYearMonth_validStringForm() {
        YearMonth ym = YearMonth.of(2026, 12);
        assertEquals("Dec 2026", VisualCommand.createLabel(ym));
    }

    @Test
    void createRow_validInputs_validStringForm() throws FinbroException {
        String label = "Jan 2026";
        String bar = "████";
        double amount = 20.1;
        VisualCommand c = new VisualCommand();
        c.createRow(label, bar, amount);

        String correctOutput = String.format("%-8s | %-30s $%.2f", label, bar, amount);

        assertEquals(correctOutput, c.output);
    }

    @Test
    void createRow_nullInputs_exception() {
        String label = null;
        String bar = "████";
        double amount = 20.1;
        VisualCommand c = new VisualCommand();

        assertThrows(FinbroException.class, () -> {
            c.createRow(label, bar, amount);
        });
    }

    @Test
    void execute_validExpenseList_noException() {
        // testing ExpenseList
        ExpenseList expenses = new ExpenseList();
        Expense e1 = new Expense(20, "food", "3 March 2026");
        Expense e2 = new Expense(10, "transport", "5 February 2026");
        expenses.add(e1);
        expenses.add(e2);

        // dummy objects to fill inputs
        Ui ui = new Ui();
        Storage storage = new Storage("");
        assertDoesNotThrow(() -> {
            new VisualCommand().execute(expenses, ui, storage);
        });
    }

    @Test
    void execute_emptyExpenseList_exception() {
        // empty ExpenseList
        ExpenseList expenses = new ExpenseList();

        // dummy objects to fill inputs
        Ui ui = new Ui();
        Storage storage = new Storage("");
        Exception e = assertThrows(FinbroException.class, () -> {
            new VisualCommand().execute(expenses, ui, storage);
        });

        String expectedMessage = "No expenses found";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
