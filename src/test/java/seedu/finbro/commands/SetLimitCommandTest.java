package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.ExpenseList;

class SetLimitCommandTest {

    @Test
    void execute_validInput_noException() {
        String input = "limit 500";

        // dummy objects to fill input
        ExpenseList expenseList = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("");

        assertDoesNotThrow(() -> {
            new SetLimitCommand().execute(input, expenseList,ui,storage);
        });
    }

    @Test
    void execute_nonNumber_exception() {
        String input = "limit abc";

        ExpenseList expenseList = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("");

        Exception e = assertThrows(FinbroException.class, () -> {
            new SetLimitCommand().execute(input, expenseList,ui,storage);
        });

        String expectedMessage = "Monthly spending limit must be a number";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void execute_outOfRange_exception() {
        String input = "limit -4";

        ExpenseList expenseList = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = new Storage("");

        Exception e = assertThrows(FinbroException.class, () -> {
            new SetLimitCommand().execute(input, expenseList,ui,storage);
        });

        String expectedMessage = "Monthly spending limit must be at least $0";
        String actualMessage = e.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
