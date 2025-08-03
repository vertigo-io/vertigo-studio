package io.vertigo.shell.labs;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "clear", description = "Clears the console.")
public class ClearCommand implements ShellCommand {
	@Override
	public void run() {
		System.out.print("\033[H\033[2J"); // Efface console dans certains terminaux
		System.out.flush();
	}

	@Override
	public void reset() {
	}
}
