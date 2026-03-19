package seedu.finbro.commands;

import seedu.finbro.utils.CommandCatalog;
import seedu.finbro.utils.ExpenseList;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.exception.FinbroException;

public class HelpCommand extends Command {
    @Override
    public void execute(ExpenseList expenseList, Ui ui, Storage storage) throws FinbroException {
        for (Command command: CommandCatalog.getSupportedCommands()) {
            ui.showCommandHelpMessage(command);
        }
    }

    @Override
    public String getHelpMessage() {
        return """
                Shows all available commands and their usage.
                Format: help
                Use: Type this anytime to see the full command list.""";
    }
}
