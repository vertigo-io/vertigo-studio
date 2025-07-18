package io.vertigo.studio.shell.commands;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.Assertion;

@Parameters(commandNames = "help", commandDescription = "Displays help information.")
public class HelpCommand implements Runnable {

	private JCommander jc;

	public HelpCommand(JCommander jc) {
		Assertion.check().isNotNull(jc);
		//---
		this.jc = jc;
	}

	public void run() {
		jc.usage();
	}
}
