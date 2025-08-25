package io.vertigo.shell.shiny;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class ShinyTable {
	private final NumberFormat numberFormat;
	private String title;
	private String noDataFound;
	private String[] header;
	private final List<String[]> rows = new ArrayList<>();

	public ShinyTable(NumberFormat numberFormat) {
		Assertion.check().isNotNull(numberFormat);
		//---
		this.numberFormat = numberFormat;
	}

	public ShinyTable title(String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.title = title;
		return this;
	}

	public ShinyTable noDataFound(String noDataFound) {
		this.noDataFound = noDataFound;
		return this;
	}

	public ShinyTable header(String... header) {
		this.header = header;
		return this;
	}

	public ShinyTable rows(List<String[]> rows) {
		this.rows.addAll(rows);
		return this;
	}

	public void print() {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(rows)
				.when(rows.isEmpty(), () -> Assertion.check().isNotBlank(noDataFound))
				.isNotNull(header);
		//---
		if (rows.isEmpty()) {
			System.out.println(noDataFound);
			return;
		}

		int columns = header.length;

		// 1. Formatage des données et détection des colonnes numériques
		List<String[]> formattedRows = new ArrayList<>();
		boolean[] isNumericColumn = new boolean[columns];

		// Détection des colonnes numériques
		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(rows, i);
		}

		// Formatage des données
		for (String[] row : rows) {
			final String[] formattedRow = new String[columns];
			for (int colIndex = 0; colIndex < columns; colIndex++) {
				String value = row[colIndex];
				if (value != null && isNumericColumn[colIndex] && isNumeric(value)) {
					formattedRow[colIndex] = formatNumber(value);
				} else {
					formattedRow[colIndex] = value != null ? value : "";
				}
			}
			formattedRows.add(formattedRow);
		}

		// 2. Calcul de la largeur max par colonne (header & données formatées)
		int[] widths = new int[columns];
		for (int i = 0; i < columns; i++) {
			widths[i] = header[i].length();
		}
		for (String[] row : formattedRows) {
			for (int i = 0; i < columns; i++) {
				if (row[i] != null && row[i].length() > widths[i]) {
					widths[i] = row[i].length();
				}
			}
		}

		// 3. Construction du format de chaque colonne avec alignement approprié
		StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < columns; i++) {
			if (isNumericColumn[i]) {
				// Alignement à droite pour les colonnes numériques
				formatBuilder.append("| %").append(widths[i]).append("s ");
			} else {
				// Alignement à gauche pour les colonnes texte
				formatBuilder.append("| %-").append(widths[i]).append("s ");
			}
		}
		formatBuilder.append("|\n");
		String format = formatBuilder.toString();

		// 4. Génération du séparateur horizontal

		// 5. Affichage de la table
		System.out.print(ShinyColors.MAGENTA_BG);
		System.out.print(ShinyColors.WHITE);
		System.out.println(title);
		System.out.print(ShinyColors.RESET);
		printLineSeparator(widths, Position.Up);
		System.out.print(ShinyColors.BLUE_BRIGHT_BG);
		System.out.printf(format, (Object[]) header);
		System.out.print(ShinyColors.RESET);
		printLineSeparator(widths, Position.Middle);
		boolean invert = false;
		for (String[] formattedRow : formattedRows) {
			if (invert) {
				System.out.print(ShinyColors.CYAN_BRIGHT_BG);
			}
			System.out.printf(format, (Object[]) formattedRow);
			if (invert) {
				System.out.print(ShinyColors.RESET);
			}
			invert = !invert;
		}
		printLineSeparator(widths, Position.Bottom);
	}

	private static enum Position {
		Up, Middle, Bottom
	}

	private static void printLineSeparator(int[] widths, Position position) {
		for (int width : widths) {
			System.out.print("+");
			for (int i = 0; i < width + 2; i++)
				System.out.print("-");
		}
		System.out.println("+");

	}

	/**
	 * Détermine si une colonne contient principalement des nombres
	 */
	private static boolean isColumnNumeric(List<String[]> rows, int columnIndex) {
		int numericCount = 0;
		int totalNonNullValues = 0;

		for (String[] row : rows) {
			if (columnIndex < row.length && row[columnIndex] != null && !row[columnIndex].trim().isEmpty()) {
				totalNonNullValues++;
				if (isNumeric(row[columnIndex])) {
					numericCount++;
				}
			}
		}

		// Considère la colonne comme numérique si au moins 80% des valeurs sont numériques
		return totalNonNullValues > 0 && (double) numericCount / totalNonNullValues >= 0.8;
	}

	/**
	 * Vérifie si une chaîne représente un nombre
	 */
	private static boolean isNumeric(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}

		String trimmed = str.trim();
		try {
			// Gère les entiers et les décimaux
			Double.parseDouble(trimmed.replace(",", ".").replace(" ", ""));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Formate un nombre avec des séparateurs de milliers
	 */
	private String formatNumber(String str) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}

		try {
			String cleaned = str.trim().replace(",", ".").replace(" ", "");
			double number = Double.parseDouble(cleaned);

			// Si c'est un nombre entier, on l'affiche sans décimales
			if (number == Math.floor(number) && !Double.isInfinite(number)) {
				return numberFormat.format((long) number);
			} else {
				return numberFormat.format(number);
			}
		} catch (NumberFormatException e) {
			return str; // Retourne la valeur originale si le formatage échoue
		}
	}
}
