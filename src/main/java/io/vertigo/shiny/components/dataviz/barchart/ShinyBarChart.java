package io.vertigo.shiny.components.dataviz.barchart;

import java.util.Arrays;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyBarChart(
		String title,
		String[] header,
		int[] values,
		ShinyBarChartStyle style) implements ShinyComponent {

	public ShinyBarChart {
	}

	// Static factory method to get a new Builder instance
	public static ShinyBarChartBuilder builder() {
		return new ShinyBarChartBuilder();
	}

	public void render(final ShinyWriter writer) {
		// Trouver la valeur maximale pour normaliser les barres
		final int maxCount = Arrays.stream(values).max().orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = Arrays.stream(header)
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		writer.println(title)
				//	shiny.getWriter().println("Total des observations : " + total);
				.println("----------------------------------------");

		for (int i = 0; i < header.length; i++) {
			final String category = header[i] != null ? header[i] : "Catégorie " + (i + 1);
			final int count = values[i];
			// Normaliser la longueur de la barre
			final int barLength = (int) ((double) count / maxCount * style.maxLength());
			final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			final String coloredBar = style.colors()[i % style.colors().length].fg(bar);
			String content = "%-" + maxLabelLength + "s | %-50s (%d)%n";
			writer.print(String.format(content, category, coloredBar, count));
		}

		writer
				.println("----------------------------------------")
				.println("Échelle : █ représente environ " + (maxCount / (double) style.maxLength()) + " unités.");
	}
}
