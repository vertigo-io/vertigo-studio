package io.vertigo.shell.systems.studio.commands.load;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.env.Env;
import io.vertigo.shell.systems.studio.StudioContext;
import io.vertigo.shell.systems.studio.StudioVar;
import io.vertigo.studio.tools.VertigoStudioMda;
import picocli.CommandLine.Command;

@Command(name = "load", description = "loads notebook config")
public final class StudioLoadCommand implements ShellCommand {

	@Override
	public void run() {
		final String notebookConfigYaml = Env.get(StudioVar.ROOT_PATH);
		StudioContext.notebookConfig = VertigoStudioMda.loadNotebookConfig(notebookConfigYaml);
	}
}
