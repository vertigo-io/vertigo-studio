package io.vertigo.shell.labs;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.shell.ShellContext;
import io.vertigo.shell.ShellUtil;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.tools.VertigoStudioMda;
import io.vertigo.studio.vertigo.domain.DomainSketch;
import io.vertigo.studio.vertigo.domain.DtSketch;
import io.vertigo.studio.vertigo.task.TaskSketch;

@Parameters(commandNames = "list", commandDescription = "Lists all sketches  with their stereotype and package.")
public final class ListSketchesCommand implements Runnable {

	@Parameter(names = { "-a", "--all" }, description = "filters all sketches")
	private boolean a = false;

	@Parameter(names = { "-d", "--data-definition" }, description = "filters data-definitions")
	private boolean d = false;

	@Parameter(names = { "-s", "--smart-types" }, description = "filters smart-types")
	private boolean s = false;

	@Parameter(names = { "-t", "--tasks" }, description = "filters tasks")
	private boolean t = false;

	@Override
	public void run() {
		final Notebook notebook = VertigoStudioMda.read(ShellContext.notebookConfig);
		String[] header = { "Key", "Name", "type" };

		final List<Sketch> sketches = notebook.getAll()
				.stream()
				.filter(s -> filter(s))
				.sorted(Comparator.comparing(s -> s.getClass().getSimpleName()))
				.collect(Collectors.toList());
		String[][] rows = new String[sketches.size()][3];
		for (int i = 0; i < sketches.size(); i++) {
			rows[i][0] = sketches.get(i).getKey().getName();
			rows[i][1] = sketches.get(i).getLocalName();
			rows[i][2] = sketches.get(i).getClass().getSimpleName();
		}
		ShellUtil.printTable("List of sketches:", header, rows);
		//---
		resetParameters();
	}

	private void resetParameters() {
		a = false;
		d = false;
		s = false;
		t = false;
	}

	private boolean filter(Sketch sketch) {
		if (a || (!d && !s && !t))
			return true;
		if (sketch instanceof DtSketch)
			return d;
		if (sketch instanceof DomainSketch)
			return s;
		if (sketch instanceof TaskSketch)
			return t;
		return false;
	}

}
