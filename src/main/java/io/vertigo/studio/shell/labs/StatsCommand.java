package io.vertigo.studio.shell;

import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

import io.vertigo.core.lang.WrappedException;
import io.vertigo.studio.notebook.Notebook;
import io.vertigo.studio.notebook.domain.DtSketch;
import io.vertigo.studio.tools.VertigoStudioMda;

@Parameters(commandNames = "stats", commandDescription = "Computes and displays statistics about the models.")
public final class StatsCommand implements Runnable {

	@Parameter(description = "The configuration file (e.g., studio-config.yaml)", required = true)
	private String configFile;

	@Override
	public void run() {
		try {
			final String configUrl = Paths.get(configFile).toUri().toURL().toExternalForm();
			final Notebook notebook = VertigoStudioMda.createNotebook(configUrl);

			System.out.println("Project Statistics:");
			System.out.println("-------------------");

			final Map<String, Long> countsByType = notebook.getAll(DtSketch.class)
					.stream()
					.collect(Collectors.groupingBy(def -> def.getMetaDefinition().getShortName(), Collectors.counting()));

			countsByType.entrySet().stream()
					.sorted(Map.Entry.comparingByKey())
					.forEach(entry -> System.out.printf(" - %-25s: %d%n", entry.getKey(), entry.getValue()));

			final long total = countsByType.values().stream().mapToLong(Long::longValue).sum();
			System.out.println("-------------------");
			System.out.printf(" > Total definitions        : %d%n", total);

		} catch (final MalformedURLException e) {
			throw WrappedException.wrap(e);
		}
	}
}
