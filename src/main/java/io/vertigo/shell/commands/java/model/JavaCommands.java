package io.vertigo.shell.commands.java.model;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.commands.java.model.list.JavaModelListCommand;
import io.vertigo.shell.commands.java.model.load.JavaModelLoadCommand;
import picocli.CommandLine.Command;

@Command(
		name = "java",
		description = "Java commands",
		subcommands = {
				JavaModelLoadCommand.class,
				JavaModelListCommand.class })

public final class JavaCommands implements ShellCommand {
	@Override
	public void run() {
	}
}
