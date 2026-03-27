package seedu.finbro.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ExpenseTest {
    //@@author Kushalshah0402
    @Test
    void constructor_validInput_fieldsCorrect() {
        Expense e = new Expense(20.0, "transport", "2026-06-15");
        assertEquals(20.0, e.amount());
        assertEquals("transport", e.category());
        assertEquals("2026-06-15", e.date());
    }
    //@@author Kushalshah0402
    @Test
    void toString_correctFormat_returnsExpectedString() {
        Expense e = new Expense(20.0, "transport", "2026-06-15");
        String expected =
                " Amount: $20.00\n" +
                "   Category: transport\n" +
                "   Date: 2026-06-15";

        assertEquals(expected, e.toString());
    }
    //@@author Kushalshah0402
    @Test
    void constructor_zeroAmount_allowed() {
        Expense e = new Expense(0.0, "misc", "2026-01-01");
        assertEquals(0.0, e.amount());
    }
    //@@author Kushalshah0402
    @Test
    void toString_roundsToTwoDecimalPlaces() {
        Expense e = new Expense(20.456, "food", "2026-01-01");
        String result = e.toString();
        assertTrue(result.contains("$20.46"));
    }

    //@@author Kushalshah0402
    @Test
    void toString_differentValues_returnsCorrectFormat() {
        Expense e = new Expense(5.5, "food", "2026-02-02");
        String expected =
                " Amount: $5.50\n" +
                "   Category: food\n" +
                "   Date: 2026-02-02";
        assertEquals(expected, e.toString());
    }

    //@@author Kushalshah0402
    @Test
    void getters_multipleCalls_consistentValues() {
        Expense e = new Expense(15.0, "shopping", "2026-03-03");
        assertEquals(15.0, e.getAmount());
        assertEquals(15.0, e.getAmount());
        assertEquals("shopping", e.getCategory());
        assertEquals("2026-03-03", e.getDate());
    }
    //@@author Kushalshah0402
    @Test
    void constructor_largeAmount_allowed() {
        Expense e = new Expense(1_000_000.50, "luxury", "2026-12-31");
        assertEquals(1_000_000.50, e.getAmount());
    }
}
