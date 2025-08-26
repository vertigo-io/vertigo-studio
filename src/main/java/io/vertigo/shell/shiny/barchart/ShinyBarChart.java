package io.vertigo.shell.shiny.barchart;

import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyBarChart {
	private String title;
	private String[] header;
	private int[] row;
	private ShinySortMode sortMode = ShinySortMode.NO;

	public ShinyBarChart(Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
	}

	public ShinyBarChart title(String title) {
		this.title = title;
		return this;
	}

	public ShinyBarChart header(List<String> header) {
		this.header = header.toArray(new String[0]);
		return this;
	}

	public ShinyBarChart header(String... header) {
		this.header = header;
		return this;
	}

	public ShinyBarChart rows(int... values) {
		this.row = values;
		return this;
	}

	public ShinyBarChart rows(List<Integer> values) {
		this.row = new int[values.size()];
		for (int i = 0; i < values.size(); i++) {
			this.row[i] = values.get(i);
		}
		return this;
	}

	public ShinyBarChart sort(ShinySortMode mode) {
		this.sortMode = mode;
		return this;
	}

	private void sort() {
		Integer[] indices = new Integer[header.length];
		for (int i = 0; i < header.length; i++) {
			indices[i] = i;
		}

		switch (sortMode) {
			case NO:
				break;
			case VALUE_ASC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(row[i1], row[i2]));
				break;
			case VALUE_DESC:
				Arrays.sort(indices, (i1, i2) -> Integer.compare(row[i2], row[i1]));
				break;
			case HEADER_ASC:
				Arrays.sort(indices, (i1, i2) -> header[i1].compareToIgnoreCase(header[i2]));
				break;
			case HEADER_DESC:
				Arrays.sort(indices, (i1, i2) -> header[i2].compareToIgnoreCase(header[i1]));
				break;
		}
		// Réappliquer le tri aux deux tableaux
		reorder(indices);
	}

	private void reorder(Integer[] indices) {
		String[] newHeader = new String[header.length];
		int[] newRow = new int[row.length];
		for (int i = 0; i < indices.length; i++) {
			newHeader[i] = header[indices[i]];
			newRow[i] = row[indices[i]];
		}
		this.header = newHeader;
		this.row = newRow;
	}

	/**
	 * Affiche un diagramme en barres en console avec des carrés pleins pour la structure BarChart.
	 * @param chart Structure contenant le titre, les étiquettes (header) et les valeurs (rows).
	 * @param maxBarLength Longueur maximale d'une barre en caractères (par défaut : 50).
	 */
	public void print(int maxBarLength) {
		sort();

		// Trouver la valeur maximale pour normaliser les barres
		int maxCount = Arrays.stream(row).max().orElse(1);
		long total = Arrays.stream(row).sum();

		// Déterminer la longueur maximale du nom de catégorie pour l'alignement
		int maxLabelLength = Arrays.stream(header)
				.mapToInt(String::length)
				.max()
				.orElse(10);

		// Afficher le diagramme
		System.out.println(title);
		//	System.out.println("Total des observations : " + total);
		System.out.println("----------------------------------------");

		for (int i = 0; i < header.length; i++) {
			String category = header[i] != null ? header[i] : "Catégorie " + (i + 1);
			int count = row[i];
			// Normaliser la longueur de la barre
			int barLength = (int) ((double) count / maxCount * maxBarLength);
			final String bar = "\u2588".repeat(Math.max(0, barLength)); // Utiliser le caractère carré plein █
			String coloredBar = ShinyColors.GREEN_BRIGHT + bar + ShinyColors.RESET;
			System.out.printf("%-" + maxLabelLength + "s | %-50s (%d)%n", category, coloredBar, count);
		}

		System.out.println("----------------------------------------");
		System.out.println("Échelle : █ représente environ " + (maxCount / (double) maxBarLength) + " unités.");
	}

}
