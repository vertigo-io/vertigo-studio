package io.vertigo.shell.systems.studio.commands.watch;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.studio.StudioContext;
import io.vertigo.studio.tools.VertigoStudioMda;
import picocli.CommandLine.Command;

@Command(name = "watch", description = "Watch source files from a configuration file.")
public final class StudioWatchCommand implements ShellCommand {

	@Override
	public void run() {
		VertigoStudioMda.watch(StudioContext.notebookConfig, false);
	}
}
