package seedu.finbro.commands;

import seedu.finbro.parser.Parser;
import seedu.finbro.finances.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpCommand extends Command {
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());
    private static final Set<String> VALID_HELP_TARGETS = Set.of(
            "help", "add", "delete", "view", "limit", "edit limit", "currency", "visual"
    );
    private static final String INVALID_HELP_USAGE = "Usage: help OR help <command> with no extra arguments.";
    private final String arg;

    public HelpCommand(String arg) {
        this.arg = arg;
    }

    //@@author zihaoalt natmloclam
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        logger.log(Level.INFO, "Help command invoked");

        if (arg.isBlank()) {
            ui.showHelpMessage(getHelpMessage());
            logger.log(Level.INFO, "Displayed general help message");
            return;
        }

        String normalizedArg = arg.trim().replaceAll("\\s+", " ").toLowerCase();
        if (!VALID_HELP_TARGETS.contains(normalizedArg)) {
            logger.log(Level.WARNING, "Invalid help target: {0}", arg);
            throw new FinbroException(INVALID_HELP_USAGE);
        }

        Command command = Parser.parse(normalizedArg);
        logger.log(Level.INFO, "Displayed help for {0} command", command.getClass().getSimpleName());
        ui.showCommandHelpMessage(command);
    }
    //@@author zihaoalt natmloclam WangZX2001
    @Override
    public String getHelpMessage() {
        return """
               Valid Commands:
               add - Add a new expense
               delete - Delete an expense
               view - View your expenses
               limit - Set/view your monthly spending limit
               edit limit - Edit your monthly spending limit
               currency - Convert expense currency
               visual - View a visualisation of your monthly expenses
               
               Enter "help <command>" for a more detailed explanation on how to use each command""";
    }
}
