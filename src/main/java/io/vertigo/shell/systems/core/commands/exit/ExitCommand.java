package io.vertigo.shell.systems.core.commands.exit;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "exit", description = "Exits the shell.")
public class ExitCommand implements ShellCommand {

	@Override
	public void run() {
		System.out.println("Shutting down the shell...");
		System.out.println("See you soon ;-)");
		System.exit(0);
	}

}
