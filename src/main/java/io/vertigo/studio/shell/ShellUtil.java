package io.vertigo.studio.shell;

import java.text.NumberFormat;

import io.vertigo.core.lang.Assertion;

public final class ShellUtil {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	public static void printTable(String title, String[] header, String[][] rows) {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(header)
				.isNotNull(rows);
		//---
		int columns = header.length;

		// 1. Formatage des données et détection des colonnes numériques
		String[][] formattedRows = new String[rows.length][columns];
		boolean[] isNumericColumn = new boolean[columns];

		// Détection des colonnes numériques
		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(rows, i);
		}

		// Formatage des données
		for (int rowIndex = 0; rowIndex < rows.length; rowIndex++) {
			for (int colIndex = 0; colIndex < columns; colIndex++) {
				String value = rows[rowIndex][colIndex];
				if (value != null && isNumericColumn[colIndex] && isNumeric(value)) {
					formattedRows[rowIndex][colIndex] = formatNumber(value);
				} else {
					formattedRows[rowIndex][colIndex] = value != null ? value : "";
				}
			}
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
		StringBuilder separator = new StringBuilder();
		for (int width : widths) {
			separator.append("+");
			for (int i = 0; i < width + 2; i++)
				separator.append("-");
		}
		separator.append("+\n");

		// 5. Affichage de la table
		System.out.println(title);
		System.out.print(separator);
		System.out.printf(format, (Object[]) header);
		System.out.print(separator);
		for (String[] row : formattedRows) {
			System.out.printf(format, (Object[]) row);
		}
		System.out.print(separator);
	}

	/**
	 * Détermine si une colonne contient principalement des nombres
	 */
	private static boolean isColumnNumeric(String[][] rows, int columnIndex) {
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
	private static String formatNumber(String str) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}

		try {
			String cleaned = str.trim().replace(",", ".").replace(" ", "");
			double number = Double.parseDouble(cleaned);

			// Si c'est un nombre entier, on l'affiche sans décimales
			if (number == Math.floor(number) && !Double.isInfinite(number)) {
				return NUMBER_FORMAT.format((long) number);
			} else {
				return NUMBER_FORMAT.format(number);
			}
		} catch (NumberFormatException e) {
			return str; // Retourne la valeur originale si le formatage échoue
		}
	}
}
