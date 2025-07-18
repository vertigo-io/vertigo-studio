package io.vertigo.studio.shell.labs;

import com.beust.jcommander.Parameters;

import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.Sketch;
import io.vertigo.studio.shell.ShellContext;
import io.vertigo.studio.tools.VertigoStudioMda;

@Parameters(commandNames = "list-sketches", commandDescription = "Lists all sketches  with their stereotype and package.")
public final class ListSketchesCommand implements Runnable {

	//    @Parameter(description = "The configuration file (e.g., studio-config.yaml)", required = true)
	//    private String configFile;

	@Override
	public void run() {
		final Notebook notebook = VertigoStudioMda.loadNotebook(ShellContext.notebookConfig);

		System.out.printf("%-20s %-40s %-40s%n", "Key", "Name", "type");
		System.out.println("------------------------------------------------------------------------------------------------------------------------");

		notebook.getAll()
				//					.stream()
				//					.sorted(Comparator.comparing(Definition::getName))
				.forEach(this::printSketchInfo);
	}

	private void printSketchInfo(final Sketch sketch) {
		final String key = sketch.getKey().getName();
		final String name = sketch.getLocalName();
		final String type = sketch.getClass().getSimpleName();

		System.out.printf("%-20s %-40s %-40s%n", key, name, type);
	}
}
