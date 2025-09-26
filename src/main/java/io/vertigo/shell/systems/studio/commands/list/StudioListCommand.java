package io.vertigo.shell.systems.studio.commands.list;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import io.vertigo.shell.ShellCommand;
import io.vertigo.shell.systems.studio.StudioContext;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.tools.VertigoStudioMda;
import io.vertigo.studio.vertigo.domain.DomainSketch;
import io.vertigo.studio.vertigo.domain.DtSketch;
import io.vertigo.studio.vertigo.task.TaskSketch;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "list", description = "Lists all sketches  with their stereotype and package.")
public final class StudioListCommand implements ShellCommand {

	@Option(names = { "-a", "--all" }, description = "filters all sketches")
	private boolean a = false;

	@Option(names = { "-d", "--data-definition" }, description = "filters data-definitions")
	private boolean d = false;

	@Option(names = { "-s", "--smart-types" }, description = "filters smart-types")
	private boolean s = false;

	@Option(names = { "-t", "--tasks" }, description = "filters tasks")
	private boolean t = false;

	@Override
	public ShinyComponent build() {
		final Notebook notebook = VertigoStudioMda.read(StudioContext.notebookConfig);
		final List<Sketch> sketches = notebook.getAll()
				.stream()
				.filter(sketch -> filter(sketch))
				.sorted(Comparator.comparing(sketch -> sketch.getClass().getSimpleName()))
				.collect(Collectors.toList());
		final List<String[]> rows = new ArrayList<>();
		for (int i = 0; i < sketches.size(); i++) {
			final Sketch sketch = sketches.get(i);
			final String[] row = new String[3];
			row[0] = sketch.getKey().name();
			row[1] = sketch.getLocalName();
			row[2] = sketch.getClass().getSimpleName();
			rows.add(row);
		}
		return Shiny.table()
				.withTitle("List of sketches:")
				.withNoDataFound("No sketch found")
				.withHeader("Key", "Name", "type")
				.addAllRows(rows)
				.build();
	}

	@Override
	public void reset() {
		a = false;
		d = false;
		s = false;
		t = false;
	}

	private boolean filter(final Sketch sketch) {
		if (a || (!d && !s && !t)) {
			return true;
		}
		if (sketch instanceof DtSketch) {
			return d;
		}
		if (sketch instanceof DomainSketch) {
			return s;
		}
		if (sketch instanceof TaskSketch) {
			return t;
		}
		return false;
	}

}
