package io.vertigo.shell.systems.core.commands.exit;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "exit", description = "Exits the shell.")
public class ExitCommand implements ShellCommand {

	@Override
	public void run() {
		writer().println("Shutting down the shell...");
		writer().println("See you soon ;-)");
		System.exit(0);
	}

}
