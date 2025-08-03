package io.vertigo.shell.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.studio.tools.VertigoStudioMda;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "clean", description = "Cleans all generated files.")
public final class CleanCommand implements ShellCommand {
	@Option(names = { "--help", "-h" }, usageHelp = true, description = "Display help information")
	private boolean help;

	//	@Parameters(description = "The configuration file (e.g., studio-config.yaml)", required = true)
	//	private String configFile;

	@Override
	public void run() {
		if (help) {
			System.out.println("Help: Use this command to clean all generated files.");
		} else {
			VertigoStudioMda.clean(ShellContext.notebookConfig);
		}
	}

	@Override
	public void reset() {
		help = false;
	}
}
