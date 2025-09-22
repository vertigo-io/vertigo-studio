package io.vertigo.shell.systems.java.commands.show;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(
		name = "show",
		description = "Java show commands",
		subcommands = {
				JavaShowModelCommand.class,
		})

public final class JavaShowCommands implements ShellCommand {

}
