package seedu.finbro.commands;

import seedu.finbro.exception.FinbroException;
import seedu.finbro.storage.Storage;
import seedu.finbro.ui.Ui;
import seedu.finbro.finances.ExpenseList;

public class EditCommand extends Command{
    private static final String COMMAND_SET_LIMIT = "limit";

    private final String arg;

    public EditCommand(String arg) {
        this.arg = arg;
    }

    //@@author natmloclam
    @Override
    public void execute(ExpenseList expenses, Ui ui, Storage storage) throws FinbroException {
        if (arg.equals(COMMAND_SET_LIMIT)) {
            new EditLimitCommand().execute(expenses, ui, storage);
        } else {
            throw new FinbroException("Invalid command");
        }
    }
    //@@author natmloclam
    @Override
    public String getHelpMessage() {
        String helpMessage = """
                Valid arguments for edit:
                - limit""";
        if (arg.equals(COMMAND_SET_LIMIT)) {
            helpMessage = new EditLimitCommand().getHelpMessage();
        }
        return helpMessage;
    }
}
