package io.vertigo.shell.systems.core.commands.help;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "help", description = "Displays help information.", helpCommand = true)
public class HelpCommand implements ShellCommand {

	@Override
	public void run() {
		//picocli will handle the help command
	}

}
