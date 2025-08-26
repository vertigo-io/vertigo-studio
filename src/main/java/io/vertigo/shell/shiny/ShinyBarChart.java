package io.vertigo.shell.shiny;

import java.util.Arrays;
import java.util.List;

import io.vertigo.shell.shiny.utils.ShinyColors;

public final class ShinyBarChart {
	private String title;
	private String[] header;
	private int[] row;

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

	public ShinyBarChart row(int[] values) {
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

	/**
	 * Affiche un diagramme en barres en console avec des carrés pleins pour la structure BarChart.
	 * @param chart Structure contenant le titre, les étiquettes (header) et les valeurs (rows).
	 * @param maxBarLength Longueur maximale d'une barre en caractères (par défaut : 50).
	 */
	public void print(int maxBarLength) {
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
