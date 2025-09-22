package io.vertigo.shell.systems.db.commands.show;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(
		name = "show",
		description = "Db show commands",
		subcommands = {
				DbShowTablesCommand.class,
				DbShowModelCommand.class
		})

public final class DbShowCommands implements ShellCommand {

}
