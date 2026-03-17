package seedu.finbro.commands;

import org.junit.jupiter.api.Test;
import seedu.finbro.utils.Expense;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseTest {
    @Test
    void constructor_validInput_fieldsCorrect() {
        Expense e = new Expense(20.0, "transport", "2026-06-15");
        assertEquals(20.0, e.getAmount());
        assertEquals("transport", e.getCategory());
        assertEquals("2026-06-15", e.getDate());
    }

    @Test
    void toString_correctFormat_returnsExpectedString() {
        Expense e = new Expense(20.0, "transport", "2026-06-15");
        String expected =
                " Amount: $20.00\n" +
                "   Category: transport\n" +
                "   Date: 2026-06-15";

        assertEquals(expected, e.toString());
    }

    @Test
    void constructor_zeroAmount_allowed() {
        Expense e = new Expense(0.0, "misc", "2026-01-01");
        assertEquals(0.0, e.getAmount());
    }
}
