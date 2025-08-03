package io.vertigo.shell.labs;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.studio.source.Source;
import picocli.CommandLine.Command;

@Command(name = "ls", description = "Lists all sources.")
public final class ListSourcesCommand implements ShellCommand {

	@Override
	public void run() {
		final List<Source> sources = ShellContext.notebookConfig.sources();
		final List<String[]> rows = new ArrayList<>();
		for (Source source : sources) {
			String[] row = new String[2];
			row[0] = source.type();
			row[1] = source.path();
			rows.add(row);
		}
		Shiny.table()
				.title("List of sources:")
				.noDataFound("No source found")
				.header("Type", "Path")
				.rows(rows)
				.print();
	}

	@Override
	public void reset() {
	}
}
