package seedu.finbro.commands;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.finances.ExpenseList;
import seedu.finbro.utils.FilterService;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.utils.Expense;
import seedu.finbro.utils.ExpenseList;
import seedu.finbro.utils.FilterService;
import seedu.finbro.utils.SortService;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final String EMPTY = "";
    private static final String ALL = "all";
    private static final String CATEGORY = "category";
    private static final String SORT_FLAG = "-sort";
    private static final String FILTER_FLAG = "-filter";
    private static final String VIEW_USAGE = "Usage: view all [-sort <month|category|amount>] OR "
            + "view <category> [-filter <month>] [-sort <month|amount>]";

    private static final Logger logger = Logger.getLogger(ViewCommand.class.getName());
    private final String arg;

    public ViewCommand(String arg) {
        this.arg = arg;
    }

    //@@author Kushalshah0402 zihaoalt AK47ofCode
    /**
     * Executes the view command to display expenses based on the provided argument.
     * The argument can specify either "all" to view all expenses, a specific category to view expenses under that
     * category, or include a sort option to sort the expenses by month, category, or amount.
     * The method validates the argument format and handles different cases accordingly,
     * throwing exceptions for invalid formats or categories.
     *
     * @param expenses The expense list to view.
     * @param ui The UI instance to display the expenses.
     * @param storage The storage instance (not used in this command but required by the interface).
     * @throws FinbroException if the command format, the category name or the sort type is invalid.
     */
    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        ParsedViewArgument parsedArg = parseViewArgument(arg);

        if (EMPTY.equals(parsedArg.target())) {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException(VIEW_USAGE);
        }

        if (parsedArg.sortType() != null && !SortService.isValidSortType(parsedArg.sortType())) {
            throw new FinbroException("Invalid sort type: " + parsedArg.sortType()
                    + "\nSupported sorts: month, category, amount");
        }

        if (ALL.equals(parsedArg.target())) {
            if (parsedArg.filterMonth() != null) {
                throw new FinbroException("Month filter is only supported with \"view <category>\".");
            }

            logger.log(Level.INFO, "Displaying all expenses");
            if (parsedArg.sortType() != null) {
                ui.showAllExpenses(SortService.sortExpenses(expenses.getAll(), parsedArg.sortType()));
            } else {
                ui.showAllExpenses(expenses.getAll());
            }
            return;
        }

        List<Expense> categoryExpenses = expenses.getCategoryExpenses(parsedArg.target());
        if (categoryExpenses.isEmpty()) {
            logger.log(Level.WARNING, "Invalid category name");
            throw new FinbroException("Current View Category only supports exact matches, or empty category.");
        }

        logger.log(Level.INFO, "Displaying expenses in category " + parsedArg.target());

        List<Expense> displayedExpenses = categoryExpenses;
        if (parsedArg.filterMonth() != null) {
            displayedExpenses = FilterService.filterExpensesByMonth(categoryExpenses, parsedArg.filterMonth());
        }

        if (parsedArg.sortType() != null) {
            if (CATEGORY.equals(parsedArg.sortType())) {
                throw new FinbroException("Category sort is only supported with \"view all\".");
            }
            displayedExpenses = SortService.sortExpenses(displayedExpenses, parsedArg.sortType());
        }

        ui.showAllExpenses(displayedExpenses);
    }


    //@@author AK47ofCode
    /**
     * Parses the raw argument string for the view command and extracts target, optional sort type,
     * and optional month filter.
     *
     * @param rawArg The raw argument string from the user input.
     * @return A ParsedViewArgument record containing the target.
     * @throws FinbroException if the argument format is invalid.
     */
    private ParsedViewArgument parseViewArgument(String rawArg) throws FinbroException {
        String trimmedArg = rawArg.trim();
        if (trimmedArg.isEmpty()) {
            return new ParsedViewArgument(EMPTY, null, null);
        }

        String[] tokens = trimmedArg.split("\\s+");
        List<String> targetTokens = new ArrayList<>();
        int index = 0;
        while (index < tokens.length && !SORT_FLAG.equals(tokens[index]) && !FILTER_FLAG.equals(tokens[index])) {
            targetTokens.add(tokens[index]);
            index++;
        }

        if (targetTokens.isEmpty()) {
            throw new FinbroException(VIEW_USAGE);
        }

        String sortType = null;
        String filterMonth = null;
        while (index < tokens.length) {
            String flag = tokens[index];
            if (index == tokens.length - 1) {
                throw new FinbroException(VIEW_USAGE);
            }

            String value = tokens[index + 1];
            if (value.startsWith("-")) {
                throw new FinbroException(VIEW_USAGE);
            }

            if (SORT_FLAG.equals(flag)) {
                if (sortType != null) {
                    throw new FinbroException("Invalid format: use at most one -sort tag.");
                }
                sortType = value;
            } else if (FILTER_FLAG.equals(flag)) {
                if (filterMonth != null) {
                    throw new FinbroException("Invalid format: use at most one -filter tag.");
                }
                filterMonth = value;
            } else {
                throw new FinbroException(VIEW_USAGE);
            }

            index += 2;
        }

        String target = String.join(" ", targetTokens);
        return new ParsedViewArgument(target, sortType, filterMonth);
    }

    //@@author AK47ofCode
    /**
     * A record to hold the parsed arguments for the view command.
     *
     * @param target The target category or "all" to view all expenses.
     * @param sortType The type of sort to apply, or null if no sort is specified.
     * @param filterMonth The month name for filtering category expenses, or null if not specified.
     */
    private record ParsedViewArgument(String target, String sortType, String filterMonth) { }

    //@@author Kushalshah0402 zihaoalt
    /**
     * Provides a help message describing the usage of the view command, including how to view all expenses,
     * view expenses by category, and how to apply filters for sorting expenses by month, category, or amount.
     * The message also includes examples of valid command formats.
     * @return A help message describing the usage of the view command.
     */
    @Override
    public String getHelpMessage() {
        return """
                Shows all recorded expenses.
                Format: view all
                Use: Displays every expense grouped by category.
                
                Shows expenses in a specific category.
                Format: view <category>
                Use: Displays only the expenses under the given category.
                Note: category names are case-insensitive.
                
                Shows all expenses sorted by a specific criterion.
                Format: view all -sort <type>
                Types:
                  month - Sort by month in chronological order
                  category - Sort by category in alphabetical order
                  amount - Sort by amount spent (highest to lowest)

                Shows one category filtered by month and optionally sorted.
                Format: view <category> [-filter <month>] [-sort <type>]
                Types:
                  month - Sort by month in chronological order
                  amount - Sort by amount spent (highest to lowest)
                Example: view transport -filter january -sort amount""";
    }
}
