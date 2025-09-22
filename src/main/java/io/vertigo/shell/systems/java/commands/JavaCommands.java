package io.vertigo.shell.systems.java.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.java.commands.load.JavaLoadCommand;
import io.vertigo.shell.systems.java.commands.show.JavaShowCommands;
import picocli.CommandLine.Command;

@Command(
		name = "java",
		description = "Java commands",
		subcommands = {
				JavaLoadCommand.class,
				JavaShowCommands.class })

public final class JavaCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
