package io.vertigo.shell.systems.studio.commands.sources;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.studio.StudioContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.studio.source.Source;
import picocli.CommandLine.Command;

@Command(name = "sources", description = "Lists all sources.")
public final class StudioSourcesCommand implements ShellCommand {

	@Override
	public ShinyComponent build() {
		final List<Source> sources = StudioContext.notebookConfig.sources();
		final List<String[]> rows = new ArrayList<>();
		for (final Source source : sources) {
			final String[] row = new String[2];
			row[0] = source.type();
			row[1] = source.path();
			rows.add(row);
		}
		return Shiny.table()
				.withTitle("List of sources:")
				.withNoDataFound("No source found")
				.withHeader("Type", "Path")
				.addAllRows(rows)
				.build();
	}

	@Override
	public void reset() {
	}
}
