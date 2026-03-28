package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;

public class CurrencyCommandTest {

    private InputStream originalIn;

    @BeforeEach
    void saveSystemIn() {
        originalIn = System.in;
    }

    @AfterEach
    void restoreSystemIn() {
        System.setIn(originalIn);
    }

    @Test
    void execute_validInput_success() {
        String simulatedInput =
                """
                        SGD
                        USD
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, null));
    }

    @Test
    void execute_sameCurrency_success() {
        String simulatedInput =
                """
                        SGD
                        SGD
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(50.0, "transport", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, null));
    }

    @Test
    void execute_lowercaseCurrency_success() {
        String simulatedInput =
                """
                        sgd
                        usd
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(80.0, "shopping", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        assertDoesNotThrow(() -> command.execute(list, ui, null));
    }

    @Test
    void execute_unsupportedSourceCurrency_throwsException() {
        String simulatedInput =
                """
                        ABC
                        USD
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertTrue(e.getMessage().contains("Unsupported currency"));
    }

    @Test
    void execute_unsupportedTargetCurrency_throwsException() {
        String simulatedInput =
                """
                        SGD
                        XYZ
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertTrue(e.getMessage().contains("Unsupported currency"));
    }

    @Test
    void execute_nonNumericEntry_throwsException() {
        String simulatedInput =
                """
                        SGD
                        USD
                        abc
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertEquals("Invalid entry number.", e.getMessage());
    }

    @Test
    void execute_zeroEntry_throwsException() {
        String simulatedInput =
                """
                        SGD
                        USD
                        0
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertEquals("Entry out of range.", e.getMessage());
    }

    @Test
    void execute_negativeEntry_throwsException() {
        String simulatedInput =
                """
                        SGD
                        USD
                        -1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertEquals("Entry out of range.", e.getMessage());
    }

    @Test
    void execute_entryOutOfRange_throwsException() {
        String simulatedInput =
                """
                        SGD
                        USD
                        2
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        list.add(new Expense(100.0, "food", "2026-01-01"));

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertEquals("Entry out of range.", e.getMessage());
    }

    @Test
    void execute_emptyExpenseList_throwsException() {
        String simulatedInput =
                """
                        SGD
                        USD
                        1
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();

        CurrencyCommand command = new CurrencyCommand();

        FinbroException e = assertThrows(FinbroException.class,
                () -> command.execute(list, ui, null));

        assertEquals("No expenses available.", e.getMessage());
    }

    @Test
    void getHelpMessage_containsCurrencyKeyword() {
        CurrencyCommand command = new CurrencyCommand();
        assertTrue(command.getHelpMessage().toLowerCase().contains("currency"));
    }
}