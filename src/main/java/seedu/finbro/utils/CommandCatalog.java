package seedu.finbro.utils;

import seedu.finbro.commands.AddCommand;
import seedu.finbro.commands.Command;
import seedu.finbro.commands.DeleteCommand;
import seedu.finbro.commands.EditLimitCommand;
import seedu.finbro.commands.HelpCommand;
import seedu.finbro.commands.SetLimitCommand;
import seedu.finbro.commands.ViewCommand;

import java.util.List;

public final class CommandCatalog {
    private static final List<Command> SUPPORTED_COMMANDS = List.of(
            new HelpCommand(),
            new AddCommand(""),
            new DeleteCommand(""),
            new EditLimitCommand(),
            new SetLimitCommand(""),
            new ViewCommand("")
    );

    public static List<Command> getSupportedCommands() {
        return SUPPORTED_COMMANDS;
    }
}
