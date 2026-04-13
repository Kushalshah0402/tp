package seedu.finbro.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;

import org.junit.jupiter.api.Test;

import seedu.finbro.ui.Ui;
import seedu.finbro.finances.ExpenseList;

public class AddCommandTest {

    @Test
    void execute_walkthroughValidInput_expenseAdded() throws Exception {

        String simulatedInput =
                """
                        20
                        food
                        2020-01-01
                        yes
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        AddCommand command = new AddCommand("");
        command.execute(list, ui, null);
        assertEquals(1, list.size());
    }

    @Test
    void execute_walkthroughUserCancels_noExpenseAdded() throws Exception {
        String input =
                """
                        20
                        food
                        2020-01-01
                        no
                        """;

        System.setIn(new ByteArrayInputStream(input.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();
        AddCommand command = new AddCommand("");
        command.execute(list, ui, null);

        assertEquals(0, list.size());
    }

    @Test
    void execute_strictModeValidInput_expenseAdded() throws Exception {
        String input = "yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        AddCommand command = new AddCommand("20 food 2020-01-01");
        command.execute(list, ui, null);
        assertEquals(1, list.size());
    }

    @Test
    void execute_strictModeZeroAmount_throwsException() {
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        AddCommand command = new AddCommand("0 food today");

        Exception exception = assertThrows(Exception.class, () -> command.execute(list, ui, null));
        assertEquals("Amount must be a positive number.", exception.getMessage());
    }

    @Test
    void execute_strictModeMissingField_throwsException() {
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        AddCommand command = new AddCommand("20 2020-01-01");
        assertThrows(Exception.class, () -> command.execute(list, ui, null));
    }

    @Test
    void execute_strictModeUserCancels_noExpenseAdded() throws Exception {
        String input = "no\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        AddCommand command = new AddCommand("20 food 2020-01-01");
        command.execute(list, ui, null);

        assertEquals(0, list.size());
    }

    @Test
    void execute_strictModeNaturalDate_expenseAdded() throws Exception {
        String input = "Y\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();

        AddCommand command = new AddCommand("20 food today");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
    }

    @Test
    void execute_strictModeNaturalDateLeadingDigitMatchesAmount_expenseAdded() throws Exception {
        String input = "yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();

        AddCommand command = new AddCommand("200.01 shopping 2 days ago");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
    }

    @Test
    void execute_strictModeMultiWordCategory_expenseAdded() throws Exception {
        String input = "yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();

        AddCommand command = new AddCommand("20 my shopping today");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
    }

    @Test
    void execute_strictModeMixedCaseCategory_caseInsensitiveLookupFindsExpense() throws Exception {
        String input = "yes\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();

        AddCommand command = new AddCommand("20 Food 2020-01-01");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
        assertEquals(1, list.getCategoryExpenses("food").size());
        assertEquals(1, list.getCategoryExpenses("FOOD").size());
    }

    @Test
    void execute_walkthroughMixedCaseCategory_caseInsensitiveLookupFindsExpense() throws Exception {
        String simulatedInput =
                """
                        20
                        Food
                        2020-01-01
                        yes
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();

        AddCommand command = new AddCommand("");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
        assertEquals(1, list.getCategoryExpenses("food").size());
        assertEquals(1, list.getCategoryExpenses("FOOD").size());
    }

    @Test
    void execute_walkthroughNumericOnlyCategory_repromptThenExpenseAdded() throws Exception {
        String simulatedInput =
                """
                        20
                        123
                        my shopping
                        2020-01-01
                        yes
                        """;

        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
        Ui ui = new Ui();
        ExpenseList list = new ExpenseList();

        AddCommand command = new AddCommand("");
        command.execute(list, ui, null);

        assertEquals(1, list.size());
    }

    @Test
    void execute_futureDate_throwsException() {
        ExpenseList list = new ExpenseList();
        Ui ui = new Ui();
        AddCommand command = new AddCommand("20 food 3000-01-01");
        assertThrows(Exception.class, () -> command.execute(list, ui, null));
    }
}

