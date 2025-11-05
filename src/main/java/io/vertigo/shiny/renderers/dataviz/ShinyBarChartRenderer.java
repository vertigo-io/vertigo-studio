package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.dataviz.chart.ShinyChart;
import io.vertigo.shiny.models.dataviz.chart.ShinyChartSerie;
import io.vertigo.shiny.models.dataviz.chart.ShinyChartType;
import io.vertigo.shiny.renderers.ShinyModelRenderer;

public final class ShinyBarChartRenderer implements ShinyModelRenderer<ShinyChart> {
	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyChart
				&& ((ShinyChart) component).chartType() == ShinyChartType.bar;
	}

	@Override
	public void render(final ShinyChart shinyChart) {
		Assertion.check()
				.isNotNull(shinyChart);
		//---// Trouver la valeur maximale pour normaliser les barres
		final ShinyBarChartStyle style = Shiny.theme().barChartStyle();
		final ShinyWriter writer = Shiny.writer();

		final double maxCount = shinyChart.series().stream()
				.flatMap(serie -> serie.data().stream())
				.mapToDouble(Double::doubleValue)
				.max()
				.orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = shinyChart.labels().stream()
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		writer.println(shinyChart.title())
				//	shiny.getWriter().println("Total des observations : " + total);
				.println("----------------------------------------");

		for (int i = 0; i < shinyChart.labels().size(); i++) {
			final String label = shinyChart.labels().get(i) != null
					? shinyChart.labels().get(i)
					: "Catégorie " + (i + 1);

			writer.println(label);

			for (int j = 0; j < shinyChart.series().size(); j++) {
				final ShinyChartSerie serie = shinyChart.series().get(j);
				final double count = serie.data().get(i);
				// Normaliser la longueur de la barre
				final int barLength = (int) (count / maxCount * style.maxLength());
				final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
				final String coloredBar = style.colors()[j % style.colors().length].fg(bar);
				String content = "%-" + maxLabelLength + "s | %-50s (%.2f)%n";
				writer.print(String.format(content, serie.name(), coloredBar, count));
			}
		}

		writer
				.println("----------------------------------------")
				.println("Échelle : █ représente environ " + (maxCount / style.maxLength()) + " unités.");
	}
}
