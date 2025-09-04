package io.vertigo.shell.systems.java.commands;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.java.commands.list.JavaListCommand;
import io.vertigo.shell.systems.java.commands.load.JavaLoadCommand;
import picocli.CommandLine.Command;

@Command(
		name = "java",
		description = "Java commands",
		subcommands = {
				JavaLoadCommand.class,
				JavaListCommand.class })

public final class JavaCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
