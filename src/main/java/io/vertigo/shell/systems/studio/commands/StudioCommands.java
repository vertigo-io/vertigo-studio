package io.vertigo.shell.systems.studio.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.studio.commands.clean.StudioCleanCommand;
import io.vertigo.shell.systems.studio.commands.generate.StudioGenerateCommand;
import io.vertigo.shell.systems.studio.commands.list.StudioListCommand;
import io.vertigo.shell.systems.studio.commands.load.StudioLoadCommand;
import io.vertigo.shell.systems.studio.commands.sources.StudioSourcesCommand;
import io.vertigo.shell.systems.studio.commands.watch.StudioWatchCommand;
import picocli.CommandLine.Command;

@Command(
		name = "studio",
		description = "Studio commands",
		subcommands = {
				StudioLoadCommand.class,
				StudioCleanCommand.class,
				StudioGenerateCommand.class,
				StudioWatchCommand.class,
				StudioListCommand.class,
				StudioSourcesCommand.class,

		})

public final class StudioCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
