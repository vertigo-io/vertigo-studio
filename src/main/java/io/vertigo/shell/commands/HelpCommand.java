package io.vertigo.shell.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.ShellCommand;

@Parameters(commandNames = "help", commandDescription = "Displays help information.")
public class HelpCommand implements ShellCommand {

	private JCommander jc;

	public HelpCommand(JCommander jc) {
		Assertion.check().isNotNull(jc);
		//---
		this.jc = jc;
	}

	public void run() {
		jc.usage();
	}

	public void reset() {
	}
}
