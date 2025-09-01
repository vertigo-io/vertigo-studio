package io.vertigo.shell.commands.studio;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(
		name = "studio",
		description = "Studio commands",
		subcommands = {
				StudioLoadCommand.class,
				StudioCleanCommand.class,
				StudioGenerateCommand.class,
				StudioWatchCommand.class,
				StudioListSketchesCommand.class,
				StudioListSourcesCommand.class,

		})

public final class StudioCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
