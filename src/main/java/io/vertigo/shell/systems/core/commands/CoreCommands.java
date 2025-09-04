package io.vertigo.shell.systems.core.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.core.commands.exit.ExitCommand;
import io.vertigo.shell.systems.core.commands.help.HelpCommand;
import io.vertigo.shell.systems.core.commands.history.HistoryCommand;
import io.vertigo.shell.systems.core.commands.ip.IpCommand;
import io.vertigo.shell.systems.core.commands.uptime.UptimeCommand;
import picocli.CommandLine.Command;

@Command(
		name = "core",
		description = "Core commands",
		subcommands = {
				ExitCommand.class,
				UptimeCommand.class,
				HistoryCommand.class,
				IpCommand.class,
				HelpCommand.class,
		})

public final class CoreCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
