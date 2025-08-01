package io.vertigo.shell.labs;

import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellCommand;

@Parameters(commandNames = "clear", commandDescription = "Clears the console.")
public class ClearCommand implements ShellCommand {
	public void run() {
		System.out.print("\033[H\033[2J"); // Efface console dans certains terminaux
		System.out.flush();
	}

	@Override
	public void reset() {
	}
}
