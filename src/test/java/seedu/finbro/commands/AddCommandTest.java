package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.ExpenseList;

public class AddCommandTest {
    //@@author Kushalshah0402
    @Test
    void execute_walkthroughValidInput_expenseAdded() throws Exception {

        // Simulated user inputs(each line = Enter key)
        String simulatedInput =
                "20\n" +
                "food\n" +
                "2026-01-01\n" +
                "yes\n";

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        Storage storage = null; // not used in walkthrough
        AddCommand command = new AddCommand("");
        command.execute(list, ui, storage);
        assertEquals(1, list.size());
    }

    //@@author Kushalshah0402
    @Test
    void execute_walkthroughUserCancels_noExpenseAdded() throws Exception {
        String input =
                "20\n" +
                "food\n" +
                "2026-01-01\n" +
                "no\n";

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        AddCommand command = new AddCommand("");
        command.execute(list, ui, null);
        assertEquals(0, list.size());
    }

    //@@author Kushalshah0402
    @Test
    void execute_strictModeValidInput_expenseAdded() throws Exception {
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = null;
        AddCommand command = new AddCommand("20 food 2026-01-01");
        command.execute(list, ui, storage);
        assertEquals(1, list.size());
    }

    //@@author Kushalshah0402
    @Test
    void execute_strictModeMissingField_throwsException() {
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        Storage storage = null;
        AddCommand command = new AddCommand("20 2026-01-01"); // missing category
        assertThrows(Exception.class, () -> command.execute(list, ui, storage));
    }
}
