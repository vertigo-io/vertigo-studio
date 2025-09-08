package io.vertigo.shell.systems.core.commands.history;

import java.util.List;

import io.vertigo.shell.ShellCommand;
import picocli.CommandLine.Command;

@Command(name = "history", description = "Displays the command history.")
public class HistoryCommand implements ShellCommand {
	private List<String> history;

	public HistoryCommand() {
		//
	}

	public HistoryCommand(final List<String> history) {
		this.history = history;
	}

	@Override
	public void run() {
		int i = 1;
		for (final String command : history) {
			writer().printf("%2d: %s%n", i++, command);
		}
	}

}
