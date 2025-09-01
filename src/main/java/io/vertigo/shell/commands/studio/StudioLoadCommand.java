package io.vertigo.shell.commands.studio;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.shell.commands.db.DbContext;
import io.vertigo.studio.tools.VertigoStudioMda;
import picocli.CommandLine.Command;

@Command(name = "load", description = "loads notebook config")
public final class StudioLoadCommand implements ShellCommand {

	@Override
	public void run() {
		final String notebookConfigYaml = DbContext.secrets.getProperty("studio.notebook");
		ShellContext.notebookConfig = VertigoStudioMda.loadNotebookConfig(notebookConfigYaml);
	}

	@Override
	public void reset() {
	}
}
