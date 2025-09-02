package io.vertigo.shell.shiny.barchart;

import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColor;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyBarChart {
	private final Shiny shiny;
	private String barChartTitle;
	private String[] barChartHeader;
	private int[] barChartRow;
	private ShinySortMode sortMode = ShinySortMode.NO;

	public ShinyBarChart(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
	}

	public ShinyBarChart title(final String title) {
		this.barChartTitle = title;
		return this;
	}

	public ShinyBarChart header(final String... header) {
		this.barChartHeader = header;
		return this;
	}

	public ShinyBarChart header(final List<String> header) {
		this.barChartHeader = header.toArray(new String[0]);
		return this;
	}

	public ShinyBarChart rows(final int... rows) {
		this.barChartRow = rows;
		return this;
	}

	public ShinyBarChart rows(final List<Integer> values) {
		this.barChartRow = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			this.barChartRow[i] = values.get(i);
		}
		return this;
	}

	public ShinyBarChart sort(final ShinySortMode mode) {
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
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartRow[i1], barChartRow[i2]));
				break;
			case VALUE_DESC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(barChartRow[i2], barChartRow[i1]));
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
		final int[] newRow = new int[barChartRow.length];
		for (int i = 0; i < indices.length; i++) {
			newHeader[i] = barChartHeader[indices[i]];
			newRow[i] = barChartRow[indices[i]];
		}
		this.barChartHeader = newHeader;
		this.barChartRow = newRow;
	}

	/**
	 * Affiche un diagramme en barres en console avec des carrés pleins pour la structure BarChart.
	 * @param chart Structure contenant le titre, les étiquettes (header) et les valeurs (rows).
	 * @param maxBarLength Longueur maximale d'une barre en caractères (par défaut : 50).
	 */
	public void print(final int maxBarLength) {
		sort();

		// Trouver la valeur maximale pour normaliser les barres
		final int maxCount = Arrays.stream(barChartRow).max().orElse(1);

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		final int maxLabelLength = Arrays.stream(barChartHeader)
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		shiny.getWriter().println(barChartTitle);
		//	shiny.getWriter().println("Total des observations : " + total);
		shiny.getWriter().println("----------------------------------------");

		for (int i = 0; i < barChartHeader.length; i++) {
			final String category = barChartHeader[i] != null ? barChartHeader[i] : "Catégorie " + (i + 1);
			final int count = barChartRow[i];
			// Normaliser la longueur de la barre
			final int barLength = (int) ((double) count / maxCount * maxBarLength);
			final String bar = "█".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			final String coloredBar = COLORS[i % COLORS.length] + bar + ShinyColors.RESET;
			shiny.getWriter().printf("%-" + maxLabelLength + "s | %-50s (%d)%n", category, coloredBar, count);
		}

		shiny.getWriter().println("----------------------------------------");
		shiny.getWriter().println("Échelle : █ représente environ " + (maxCount / (double) maxBarLength) + " unités.");
	}

	private static ShinyColor[] COLORS = {
			ShinyColors.BLUE,
			ShinyColors.BLUE.bright() };

	//	private static String[] COLORS = {
	//			ShinyColors.RED,
	//			ShinyColors.GREEN,
	//			ShinyColors.YELLOW,
	//			ShinyColors.BLUE,
	//			ShinyColors.MAGENTA,
	//			ShinyColors.CYAN };
	//	private static String[] COLORS = {
	//			ShinyColors.RED_BRIGHT,
	//			ShinyColors.GREEN_BRIGHT,
	//			ShinyColors.YELLOW_BRIGHT,
	//			ShinyColors.BLUE_BRIGHT,
	//			ShinyColors.MAGENTA_BRIGHT,
	//			ShinyColors.CYAN_BRIGHT };
}
