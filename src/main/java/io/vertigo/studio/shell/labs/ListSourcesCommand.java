package io.vertigo.studio.shell.labs;

import com.beust.jcommander.Parameters;

import io.vertigo.studio.shell.ShellContext;
import io.vertigo.studio.source.Source;

@Parameters(commandNames = "ls", commandDescription = "Lists all sources.")
public final class ListSourcesCommand implements Runnable {

	@Override
	public void run() {
		System.out.printf("%-50s %-100s", "Type", "Path");
		System.out.println("------------------------------------------------------------------------------------------------------------------------");

		ShellContext.notebookConfig.sources()
				.stream()
				.forEach(this::printInfo);
	}

	private void printInfo(final Source source) {
		System.out.printf("%-50s %-100s%n", source.type(), source.path());
	}
}
