package io.vertigo.shiny.components.dataviz.barchart;

import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyBarChart implements ShinyComponent {
	private String barChartTitle;
	private String[] barChartHeader;
	private int[] barChartValues;
	private ShinySortMode sortMode = ShinySortMode.NO;
	private ShinyBarChartStyle barChartStyle;

	public ShinyBarChart() {
		barChartStyle = Shiny.theme().barChartStyle();
	}

	public ShinyBarChart withStyle(final ShinyBarChartStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.barChartStyle = style;
		return this;
	}

	public ShinyBarChart withTitle(final String title) {
		this.barChartTitle = title;
		return this;
	}

	public ShinyBarChart withHeader(final String... header) {
		this.barChartHeader = header;
		return this;
	}

	public ShinyBarChart withHeader(final List<String> header) {
		this.barChartHeader = header.toArray(new String[0]);
		return this;
	}

	public ShinyBarChart withValues(final int... values) {
		this.barChartValues = values;
		return this;
	}

	public ShinyBarChart withValues(final List<Integer> values) {
		this.barChartValues = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			this.barChartValues[i] = values.get(i);
		}
		return this;
	}

	public ShinyBarChart withSort(final ShinySortMode mode) {
		this.sortMode = mode;
		return this;
	}

	private void sort() {
		final Integer[] indices = new Integer[barChartHeader.length];
		for (int i = 0; i < barChartHeader.length; i++) {
			indices[i] = i;
		}

		switch (sortMode) {
			case NO:
				break;
			case VALUE_ASC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartValues[i1], barChartValues[i2]));
				break;
			case VALUE_DESC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartValues[i2], barChartValues[i1]));
				break;
			case HEADER_ASC:
				Arrays.sort(indices, (i1, i2) -> barChartHeader[i1].compareToIgnoreCase(barChartHeader[i2]));
				break;
			case HEADER_DESC:
				Arrays.sort(indices, (i1, i2) -> barChartHeader[i2].compareToIgnoreCase(barChartHeader[i1]));
				break;
		}
		// Réappliquer le tri aux deux tableaux
		reorder(indices);
	}

	private void reorder(final Integer[] indices) {
		final String[] newHeader = new String[barChartHeader.length];
		final int[] newValues = new int[barChartValues.length];
		for (int i = 0; i < indices.length; i++) {
			newHeader[i] = barChartHeader[indices[i]];
			newValues[i] = barChartValues[indices[i]];
		}
		this.barChartHeader = newHeader;
		this.barChartValues = newValues;
	}

	public void render(final ShinyWriter writer) {
		sort();

		// Trouver la valeur maximale pour normaliser les barres
		final int maxCount = Arrays.stream(barChartValues).max().orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = Arrays.stream(barChartHeader)
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		writer.println(barChartTitle)
				//	shiny.getWriter().println("Total des observations : " + total);
				.println("----------------------------------------");

		for (int i = 0; i < barChartHeader.length; i++) {
			final String category = barChartHeader[i] != null ? barChartHeader[i] : "Catégorie " + (i + 1);
			final int count = barChartValues[i];
			// Normaliser la longueur de la barre
			final int barLength = (int) ((double) count / maxCount * barChartStyle.maxLength());
			final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			final String coloredBar = barChartStyle.colors()[i % barChartStyle.colors().length].fg(bar);
			String content = "%-" + maxLabelLength + "s | %-50s (%d)%n";
			writer.print(String.format(content, category, coloredBar, count));
		}

		writer
				.println("----------------------------------------")
				.println("Échelle : █ représente environ " + (maxCount / (double) barChartStyle.maxLength()) + " unités.");
	}
}