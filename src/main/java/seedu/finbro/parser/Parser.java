package seedu.finbro.parser;

import seedu.finbro.commands.CurrencyCommand;
import seedu.finbro.commands.AddCommand;
import seedu.finbro.commands.Command;
import seedu.finbro.commands.DeleteCommand;
import seedu.finbro.commands.EditLimitCommand;
import seedu.finbro.commands.HelpCommand;
import seedu.finbro.commands.SetLimitCommand;
import seedu.finbro.commands.ViewCommand;
import seedu.finbro.commands.VisualCommand;
import seedu.finbro.exception.FinbroException;

/**
 * Parses raw user input into executable Finbro commands.
 */
public class Parser {
    private static final String COMMAND_ADD = "add";
    private static final String COMMAND_VIEW = "view";
    private static final String COMMAND_DELETE = "delete";
    private static final String COMMAND_SET_LIMIT = "limit";
    private static final String COMMAND_EDIT_LIMIT = "edit limit";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_VISUAL = "visual";
    private static final String COMMAND_CURRENCY = "currency";

    /**
     * Parses a raw user input string into the corresponding command.
     *
     * @param input Raw command entered by the user.
     * @return Parsed command object.
     * @throws FinbroException If the command format is invalid.
     */
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
        case COMMAND_EDIT_LIMIT -> parseEditLimitCommand(argument);
        case COMMAND_VISUAL -> parseVisualCommand(argument);
        case COMMAND_CURRENCY -> parseCurrencyCommand(argument);
        
        default -> throw new FinbroException("Invalid command.");
        };
    }

    private static Command parseEditLimitCommand(String argument) throws FinbroException {
        verifyNoArguments(argument);
        return new EditLimitCommand();
    }

    //@@author WangZX2001
    private static Command parseVisualCommand(String argument) throws FinbroException {
        verifyNoArguments(argument);
        return new VisualCommand();
    }

    //@@author WangZX2001
    private static Command parseCurrencyCommand(String argument) throws FinbroException {
        verifyNoArguments(argument);
        return new CurrencyCommand();
    }

    //@@author WangZX2001
    private static void verifyNoArguments(String argument) throws FinbroException {
        if (!argument.isEmpty()) {
            throw new FinbroException("Invalid command.");
        }
    }

    /**
     * Extracts the command word from the raw user input.
     *
     * @param input Raw command entered by the user.
     * @return Lowercase command word.
     */
    //@@author zihaoalt natmloclam
    public static String filterCommand(String input) {
        String trimmed = input.strip();
        String lower = trimmed.toLowerCase();

        // Support multi-word commands by matching the longest known prefix first.
        if (lower.startsWith(COMMAND_EDIT_LIMIT)
                && (lower.length() == COMMAND_EDIT_LIMIT.length()
                || Character.isWhitespace(lower.charAt(COMMAND_EDIT_LIMIT.length())))) {
            return COMMAND_EDIT_LIMIT;
        }

        String[] words = trimmed.split(" ", 2);
        return words[0].strip().toLowerCase();
    }

    /**
     * Extracts the argument portion from the raw user input.
     *
     * @param input Raw command entered by the user.
     * @return Lowercase trimmed argument string, or an empty string if none exists.
     */
    //@@author zihaoalt natmloclam
    public static String filterArg(String input) {
        String trimmed = input.strip();
        if (trimmed.isEmpty()) {
            return "";
        }

        String lower = trimmed.toLowerCase();
        if (lower.startsWith(COMMAND_EDIT_LIMIT)
                && (lower.length() == COMMAND_EDIT_LIMIT.length()
                || Character.isWhitespace(lower.charAt(COMMAND_EDIT_LIMIT.length())))) {
            String rest = trimmed.substring(COMMAND_EDIT_LIMIT.length()).strip();
            return rest.toLowerCase();
        }

        String[] splitSentence = trimmed.split(" ");
        if (splitSentence.length < 2) {
            return "";
        }
        String[] words = trimmed.split(" ", 2);
        return words[1].strip().toLowerCase();
    }
}
