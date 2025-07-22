package io.vertigo.studio.shell.commands;

import com.beust.jcommander.Parameters;

import io.vertigo.studio.shell.ShellContext;
import io.vertigo.studio.tools.VertigoStudioMda;

@Parameters(commandNames = "watch", commandDescription = "Watch source files from a configuration file.")
public final class WatchCommand implements Runnable {

	@Override
	public void run() {
		VertigoStudioMda.watch(ShellContext.notebookConfig, false);
	}
}
