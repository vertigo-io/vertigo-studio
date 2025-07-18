package io.vertigo.studio.shell.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.studio.shell.ShellContext;
import io.vertigo.studio.tools.VertigoStudioMda;

@Parameters(commandDescription = "Generate source files from a configuration file.")
public final class GenerateCommand implements Runnable {
	@Parameter(names = { "--help", "-h" }, help = true, description = "Display help information")
	private boolean help;
	//    @Parameter(description = "The configuration file (e.g., studio-config.yaml)", required = true)
	//    private String configFile;

	@Override
	public void run() {
		if (help) {
			System.out.println("Help: Use this command to generate source files.");
		} else {
			VertigoStudioMda.generate(ShellContext.notebookConfig);
		}
	}
}
