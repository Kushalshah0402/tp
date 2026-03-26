package seedu.finbro.commands;

import seedu.finbro.parser.Parser;
import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HelpCommand extends Command {
    private static final Logger logger = Logger.getLogger(HelpCommand.class.getName());
    private final String arg;

    public HelpCommand(String arg) {
        this.arg = arg;
    }

    /**
     * @author zihaoalt natmloclam
     */
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        logger.log(Level.INFO, "Help command invoked");

        Command command;
        try {
            command = Parser.parse(arg);
        } catch (FinbroException e) {
            ui.showHelpMessage(getHelpMessage());
            logger.log(Level.WARNING, "Invalid command \"{0}\", showing default help message", arg);
            return;
        }

        logger.log(Level.INFO, "Displayed help for {0} commands", command.getClass().getSimpleName());
        ui.showCommandHelpMessage(command);
    }
    /**
     * @author zihaoalt natmloclam
     */
    @Override
    public String getHelpMessage() {
        return """
               Valid Commands:
               add - Add a new expense
               delete - Delete an expense
               view - View your expenses
               limit - Set/view your monthly spending limit
               edit limit - Edit your monthly spending limit
               
               Enter "help <command>" for a more detailed explanation on how to use each command""";
    }
}
