package io.vertigo.studio.shell;

import io.vertigo.core.lang.Assertion;

public final class ShellUtil {
	public static void printTable(String[] header, String[][] rows) {
		Assertion.check()
				.isNotNull(header)
				.isNotNull(rows);
		//---
		int columns = header.length;
		// 1. Calcul de la largeur max par colonne (header & données)
		int[] widths = new int[columns];
		for (int i = 0; i < columns; i++) {
			widths[i] = header[i].length();
		}
		for (String[] row : rows) {
			for (int i = 0; i < columns; i++) {
				if (row[i] != null && row[i].length() > widths[i]) {
					widths[i] = row[i].length();
				}
			}
		}

		// 2. Construction du format de chaque colonne (ex. "%-10s |")
		StringBuilder formatBuilder = new StringBuilder();
		for (int width : widths) {
			formatBuilder.append("| %-").append(width).append("s ");
		}
		formatBuilder.append("|\n");
		String format = formatBuilder.toString();

		// 3. Génération du séparateur horizontal
		StringBuilder separator = new StringBuilder();
		for (int width : widths) {
			separator.append("+");
			for (int i = 0; i < width + 2; i++)
				separator.append("-");
		}
		separator.append("+\n");

		// 4. Affichage de la table
		System.out.print(separator);
		System.out.printf(format, (Object[]) header);
		System.out.print(separator);
		for (String[] row : rows) {
			System.out.printf(format, (Object[]) row);
		}
		System.out.print(separator);
	}

}
