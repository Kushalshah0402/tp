package seedu.finbro.commands;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @Test
    void constructor_validInput_fieldsCorrect() {
        Expense e = new Expense(20.0, "transport", "15 June 2026");

        assertEquals(20.0, e.getAmount());
        assertEquals("transport", e.getCategory());
        assertEquals("15 June 2026", e.getDate());
    }

    @Test
    void toString_correctFormat_returnsExpectedString() {
        Expense e = new Expense(20.0, "transport", "15 June 2026");

        String expected =
                " Amount: $20.00\n" +
                "   Category: transport\n" +
                "   Date: 15 June 2026";

        assertEquals(expected, e.toString());
    }

    @Test
    void constructor_zeroAmount_allowed() {
        Expense e = new Expense(0.0, "misc", "1 Jan 2026");

        assertEquals(0.0, e.getAmount());
    }
}