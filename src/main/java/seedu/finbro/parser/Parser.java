package seedu.finbro.parser;

import seedu.finbro.commands.CurrencyCommand;
import seedu.finbro.commands.EditCommand;
import seedu.finbro.commands.AddCommand;
import seedu.finbro.commands.Command;
import seedu.finbro.commands.DeleteCommand;
import seedu.finbro.commands.HelpCommand;
import seedu.finbro.commands.SetLimitCommand;
import seedu.finbro.commands.ViewCommand;
import seedu.finbro.commands.VisualCommand;
import seedu.finbro.exception.FinbroException;

public class Parser {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_VIEW = "view";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SET_LIMIT = "limit";
    private static final String COMMAND_EDIT = "edit";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_VISUAL = "visual";
    private static final String COMMAND_CURRENCY = "currency";

    //@@author zihaoalt natmloclam
    public static Command parse(String input) throws FinbroException {
        String commandWord = filterCommand(input);
        String argument = filterArg(input);

        return switch (commandWord) {
            case COMMAND_HELP -> new HelpCommand(argument);
            case COMMAND_ADD -> new AddCommand(argument);
            case COMMAND_VIEW -> new ViewCommand(argument);
            case COMMAND_DELETE -> new DeleteCommand(argument);
            case COMMAND_SET_LIMIT -> new SetLimitCommand(argument);
            case COMMAND_EDIT -> new EditCommand(argument);
            case COMMAND_VISUAL -> {
                if (!argument.isEmpty()) {
                    throw new FinbroException("Invalid command.");
                }
                yield new VisualCommand();
            }
            case COMMAND_CURRENCY -> {
                if (!argument.isEmpty()) {
                    throw new FinbroException("Invalid command.");
                }
                yield new CurrencyCommand();
            }
            default -> throw new FinbroException("Invalid command.");
        };
    }

    //@@author zihaoalt natmloclam
    public static String filterCommand(String input) {
        String[] words = input.split(" ", 2);
        // command is case-insensitive
        return words[0].strip().toLowerCase();
    }

    //@@author zihaoalt natmloclam
    public static String filterArg(String input) {
        String[] splitSentence = input.split(" ");
        if (splitSentence.length < 2) {
            return "";
        }
        String[] words = input.split(" ", 2);
        // argument is case-insensitive
        return words[1].strip().toLowerCase();
    }
}
