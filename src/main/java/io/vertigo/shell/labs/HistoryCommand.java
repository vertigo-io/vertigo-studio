package io.vertigo.shell.labs;

import java.util.List;

import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellCommand;

@Parameters(commandNames = "history", commandDescription = "Displays the command history.")
public class HistoryCommand implements ShellCommand {
	private final List<String> history;

	public HistoryCommand(List<String> history) {
		this.history = history;
	}

	public void run() {
		int i = 1;
		for (String command : history) {
			System.out.printf("%2d: %s%n", i++, command);
		}
	}

	@Override
	public void reset() {
	}
}
