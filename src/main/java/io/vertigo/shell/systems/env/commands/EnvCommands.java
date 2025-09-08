package io.vertigo.shell.systems.env.commands;

import io.vertigo.shell.systems.env.commands.list.EnvListCommand;
import io.vertigo.shell.systems.env.commands.set.EnvSetCommand;
import picocli.CommandLine.Command;

@Command(
		name = "env",
		mixinStandardHelpOptions = true,
		description = "Env commands",
		subcommands = {
				EnvSetCommand.class,
				EnvListCommand.class
		})
public class EnvCommands {
	// This is a container for the env commands
}
