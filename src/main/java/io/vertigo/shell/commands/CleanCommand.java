package io.vertigo.shell.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.studio.tools.VertigoStudioMda;

@Parameters(commandNames = "clean", commandDescription = "Cleans all generated files.")
public final class CleanCommand implements ShellCommand {
	@Parameter(names = { "--help", "-h" }, help = true, description = "Display help information")
	private boolean help;

	//	@Parameter(description = "The configuration file (e.g., studio-config.yaml)", required = true)
	//	private String configFile;

	@Override
	public void run() {
		if (help) {
			System.out.println("Help: Use this command to clean all generated files.");
			return;
		} else {
			VertigoStudioMda.clean(ShellContext.notebookConfig);
		}
	}

	@Override
	public void reset() {
		help = false;
	}
}
