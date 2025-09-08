package io.vertigo.shell.systems.studio.commands.sources;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.studio.StudioContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.studio.source.Source;
import picocli.CommandLine.Command;

@Command(name = "sources", description = "Lists all sources.")
public final class StudioSourcesCommand implements ShellCommand {

	@Override
	public void run() {
		final ShinyWriter writer = Shiny.writer();

		final List<Source> sources = StudioContext.notebookConfig.sources();
		final List<String[]> rows = new ArrayList<>();
		for (final Source source : sources) {
			final String[] row = new String[2];
			row[0] = source.type();
			row[1] = source.path();
			rows.add(row);
		}
		Shiny.table()
				.title("List of sources:")
				.noDataFound("No source found")
				.header("Type", "Path")
				.rows(rows)
				.render(writer);
	}

	@Override
	public void reset() {
	}
}
