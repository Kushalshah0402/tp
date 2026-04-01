package seedu.finbro.commands;

import seedu.finbro.utils.ExpenseList;
import seedu.finbro.utils.SortService;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ViewCommand extends Command {
    private static final String EMPTY = "";
    private static final String ALL = "all";
    private static final String CATEGORY = "category";
    private static final String SORT_FLAG = "-sort";

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

        if (parsedArg.sortType() != null) {
            handleSortedView(expenses, ui, parsedArg.target(), parsedArg.sortType());
            return;
        }

        switch (parsedArg.target()) {
        case EMPTY -> {
            logger.log(Level.WARNING, "Invalid command format");
            throw new FinbroException("Usage: view all [-sort <month|category|amount>] OR "
                    + "view <category> [-sort <month|amount>]");
        }
        case ALL -> {
            logger.log(Level.INFO, "Displaying all expenses");
            ui.showAllExpenses(expenses.getAll());
        }
        default -> {
            if (expenses.getCategoryExpenses(parsedArg.target()).isEmpty()) {
                logger.log(Level.WARNING, "Invalid category name");
                throw new FinbroException("Current View Category only supports exact matches, or empty category.");
            }
            logger.log(Level.INFO, "Displaying expenses in category " + parsedArg.target());
            ui.showAllExpenses(expenses.getCategoryExpenses(parsedArg.target()));
        }
        }
    }

    //@@author AK47ofCode
    /**
     * Handles sorted view of expenses.
     *
     * @param expenses The expense list.
     * @param ui The UI instance.
     * @throws FinbroException if sort type is invalid or no expenses exist.
     */
    private void handleSortedView(ExpenseList expenses, Ui ui,
                                    String target, String sortType) throws FinbroException {
        logger.log(Level.INFO, "Handling sorted view: target={0}, sort={1}", new Object[]{target, sortType});

        if (!SortService.isValidSortType(sortType)) {
            throw new FinbroException("Invalid sort type: " + sortType +
                    "\nSupported sorts: month, category, amount");
        }

        if (ALL.equals(target)) {
            ui.showAllExpenses(SortService.sortExpenses(expenses.getAll(), sortType));
            return;
        }

        if (CATEGORY.equals(sortType)) {
            throw new FinbroException("Category sort is only supported with \"view all\".");
        }

        if (expenses.getCategoryExpenses(target).isEmpty()) {
            logger.log(Level.WARNING, "Invalid category name");
            throw new FinbroException("Current View Category only supports exact matches, or empty category.");
        }

        ui.showAllExpenses(SortService.sortExpenses(expenses.getCategoryExpenses(target), sortType));
    }

    //@@author AK47ofCode
    /**
     * Parses the raw argument string for the view command and extracts the target and sort type if present.
     *
     * @param rawArg The raw argument string from the user input.
     * @return A ParsedViewArgument record containing the target.
     * @throws FinbroException if the argument format is invalid.
     */
    private ParsedViewArgument parseViewArgument(String rawArg) throws FinbroException {
        String trimmedArg = rawArg.trim();
        if (trimmedArg.isEmpty()) {
            return new ParsedViewArgument(EMPTY, null);
        }

        String[] tokens = trimmedArg.split("\\s+");
        int sortIndex = -1;
        for (int i = 0; i < tokens.length; i++) {
            if (SORT_FLAG.equals(tokens[i])) {
                if (sortIndex != -1) {
                    throw new FinbroException("Invalid format: use at most one -sort tag.");
                }
                sortIndex = i;
            }
        }

        if (sortIndex == -1) {
            return new ParsedViewArgument(trimmedArg, null);
        }

        if (sortIndex == 0 || sortIndex != tokens.length - 2) {
            throw new FinbroException("Usage: view all -sort <month|category|amount> OR "
                    + "view <category> -sort <month|amount>");
        }

        String target = String.join(" ", java.util.Arrays.copyOfRange(tokens, 0, sortIndex));
        String sortType = tokens[tokens.length - 1];
        return new ParsedViewArgument(target, sortType);
    }

    //@@author AK47ofCode
    /**
     * A record to hold the parsed arguments for the view command that has the target category and optional sort type.
     *
     * @param target The target category or "all" to view all expenses.
     * @param sortType The type of sort to apply, or null if no sort is specified.
     */
    private record ParsedViewArgument(String target, String sortType) { }

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
                
                Shows all expenses filtered and sorted by a specific criterion.
                Format: view all -sort <type>
                Types:
                  month - Sort by month in chronological order
                  category - Sort by category in alphabetical order
                  amount - Sort by amount spent (highest to lowest)

                Shows one category filtered and sorted.
                Format: view <category> -sort <type>
                Types:
                  month - Sort by month in chronological order
                  amount - Sort by amount spent (highest to lowest)
                Example: view transport -sort amount""";
    }
}
