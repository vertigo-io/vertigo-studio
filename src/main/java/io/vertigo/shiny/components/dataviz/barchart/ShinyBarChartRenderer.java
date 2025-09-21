package io.vertigo.shiny.components.dataviz.barchart;

import java.util.Arrays;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyBarChartRenderer implements ShinyComponentRenderer<ShinyBarChart> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyBarChart;
	}

	@Override
	public void render(final ShinyBarChart shinyBarChart, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyBarChart);
		Assertion.check().isNotNull(writer);
		//---// Trouver la valeur maximale pour normaliser les barres
		final int maxCount = Arrays.stream(shinyBarChart.values()).max().orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = Arrays.stream(shinyBarChart.header())
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		writer.println(shinyBarChart.title())
				//	shiny.getWriter().println("Total des observations : " + total);
				.println("----------------------------------------");

		for (int i = 0; i < shinyBarChart.header().length; i++) {
			final String category = shinyBarChart.header()[i] != null ? shinyBarChart.header()[i] : "Catégorie " + (i + 1);
			final int count = shinyBarChart.values()[i];
			// Normaliser la longueur de la barre
			final int barLength = (int) ((double) count / maxCount * shinyBarChart.style().maxLength());
			final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			final String coloredBar = shinyBarChart.style().colors()[i % shinyBarChart.style().colors().length].fg(bar);
			String content = "%-" + maxLabelLength + "s | %-50s (%d)%n";
			writer.print(String.format(content, category, coloredBar, count));
		}

		writer
				.println("----------------------------------------")
				.println("Échelle : █ représente environ " + (maxCount / (double) shinyBarChart.style().maxLength()) + " unités.");
	}
}
