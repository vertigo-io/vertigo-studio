package io.vertigo.shiny.renderers.dataviz;

import java.util.Arrays;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.bar.ShinyBarChart;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinyBarChartRenderer implements ShinyModelRenderer<ShinyBarChart> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyBarChart;
	}

	@Override
	public void render(final ShinyBarChart shinyBarChart) {
		Assertion.check()
				.isNotNull(shinyBarChart);
		//---// Trouver la valeur maximale pour normaliser les barres
		final ShinyBarChartStyle style = Shiny.theme().barChartStyle();
		final ShinyWriter writer = Shiny.writer();
		final int maxCount = Arrays.stream(shinyBarChart.values()).max().orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = shinyBarChart.labels().stream()
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		writer.println(shinyBarChart.title())
				//	shiny.getWriter().println("Total des observations : " + total);
				.println("----------------------------------------");

		for (int i = 0; i < shinyBarChart.labels().size(); i++) {
			final String label = shinyBarChart.labels().get(i) != null
					? shinyBarChart.labels().get(i)
					: "Catégorie " + (i + 1);
			final int count = shinyBarChart.values()[i];
			// Normaliser la longueur de la barre
			final int barLength = (int) ((double) count / maxCount * style.maxLength());
			final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			final String coloredBar = style.colors()[i % style.colors().length].fg(bar);
			String content = "%-" + maxLabelLength + "s | %-50s (%d)%n";
			writer.print(String.format(content, label, coloredBar, count));
		}

		writer
				.println("----------------------------------------")
				.println("Échelle : █ représente environ " + (maxCount / (double) style.maxLength()) + " unités.");
	}
}
