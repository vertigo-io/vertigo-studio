package io.vertigo.shell.commands;

import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.Shell;

@Parameters(commandNames = "exit", commandDescription = "Exits the shell.")
public class ExitCommand implements Runnable {

	private final Shell shell;

	public ExitCommand(Shell shell) {
		Assertion.check().isNotNull(shell);
		//---
		this.shell = shell;
	}

	public void run() {
		System.out.println("Shutting down the shell...");
		shell.stop();
	}
}
