package io.vertigo.shell.labs;

import java.util.List;

import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.ShellContext;
import io.vertigo.shell.Shiny;
import io.vertigo.studio.source.Source;

@Parameters(commandNames = "ls", commandDescription = "Lists all sources.")
public final class ListSourcesCommand implements ShellCommand {

	@Override
	public void run() {
		List<Source> sources = ShellContext.notebookConfig.sources();
		String rows[][] = new String[sources.size()][2];
		for (int i = 0; i < sources.size(); i++) {
			rows[i][0] = sources.get(i).type();
			rows[i][1] = sources.get(i).path();
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
