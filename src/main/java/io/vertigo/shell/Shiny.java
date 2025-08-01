package io.vertigo.shell;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

import io.vertigo.core.lang.Assertion;

public final class Shiny {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getNumberInstance(ShellContext.LOCALE);

	public static enum Color {
		// Couleurs de police standard
		BLACK("\u001B[30m"),
		RED("\u001B[31m"),
		GREEN("\u001B[32m"),
		YELLOW("\u001B[33m"),
		BLUE("\u001B[34m"),
		MAGENTA("\u001B[35m"),
		CYAN("\u001B[36m"),
		WHITE("\u001B[37m"),

		// Couleurs de police en gras (bold)
		BLACK_BOLD("\u001B[1;30m"),
		RED_BOLD("\u001B[1;31m"),
		GREEN_BOLD("\u001B[1;32m"),
		YELLOW_BOLD("\u001B[1;33m"),
		BLUE_BOLD("\u001B[1;34m"),
		MAGENTA_BOLD("\u001B[1;35m"),
		CYAN_BOLD("\u001B[1;36m"),
		WHITE_BOLD("\u001B[1;37m"),

		// Couleurs de police plus claires (bright)
		BLACK_BRIGHT("\u001B[90m"),
		RED_BRIGHT("\u001B[91m"),
		GREEN_BRIGHT("\u001B[92m"),
		YELLOW_BRIGHT("\u001B[93m"),
		BLUE_BRIGHT("\u001B[94m"),
		MAGENTA_BRIGHT("\u001B[95m"),
		CYAN_BRIGHT("\u001B[96m"),
		WHITE_BRIGHT("\u001B[97m"),

		// Couleurs d'arrière-plan standard
		BLACK_BG("\u001B[40m"),
		RED_BG("\u001B[41m"),
		GREEN_BG("\u001B[42m"),
		YELLOW_BG("\u001B[43m"),
		BLUE_BG("\u001B[44m"),
		MAGENTA_BG("\u001B[45m"),
		CYAN_BG("\u001B[46m"),
		WHITE_BG("\u001B[47m"),

		// Couleurs d'arrière-plan plus claires (bright)
		BLACK_BRIGHT_BG("\u001B[100m"),
		RED_BRIGHT_BG("\u001B[101m"),
		GREEN_BRIGHT_BG("\u001B[102m"),
		YELLOW_BRIGHT_BG("\u001B[103m"),
		BLUE_BRIGHT_BG("\u001B[104m"),
		MAGENTA_BRIGHT_BG("\u001B[105m"),
		CYAN_BRIGHT_BG("\u001B[106m"),
		WHITE_BRIGHT_BG("\u001B[107m"),

		// Styles supplémentaires
		UNDERLINE("\u001B[4m"),
		BLINK("\u001B[5m"), // Note : support limité sur certains terminaux
		INVERSE("\u001B[7m"),

		// Réinitialisation
		RESET("\u001B[0m");

		private final String code;

		private Color(String code) {
			this.code = code;
		}

		public String code() {
			return code;
		}
	}

	// Réinitialisation
	//
	//	private static class Theme {
	//		private static class Table {
	//			Color title;
	//			Color header;
	//			Color row1;
	//			Color row;
	//		}
	//		
	//	}
	public static Table table() {
		return new Table();
	}

	public static class Table {
		private String title;
		private String noDataFound;
		private String[] header;
		private String[][] rows;

		private Table() {
		}

		public Table title(String title) {
			this.title = title;
			return this;
		}

		public Table noDataFound(String noDataFound) {
			this.noDataFound = noDataFound;
			return this;
		}

		public Table header(String... header) {
			this.header = header;
			return this;
		}

		public Table rows(String[][] rows) {
			this.rows = rows;
			return this;
		}

		public Table rows(List<String[]> rows) {
			rows(rows.toArray(new String[0][]));
			return this;
		}

		public void print() {
			Assertion.check()
					.isNotBlank(title)
					.isNotBlank(noDataFound)
					.isNotNull(header)
					.isNotNull(rows);
			//---
			if (Arrays.asList(rows).isEmpty()) {
				System.out.println(noDataFound);
				return;
			}

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
			System.out.print(Color.MAGENTA_BG.code());
			System.out.print(Color.WHITE.code());
			System.out.println(title);
			System.out.print(Color.RESET.code());
			System.out.print(separator);
			System.out.print(Color.BLUE_BRIGHT_BG.code());
			System.out.printf(format, (Object[]) header);
			System.out.print(Color.RESET.code());
			System.out.print(separator);
			boolean invert = false;
			for (String[] row : formattedRows) {
				if (invert) {
					System.out.print(Color.CYAN_BRIGHT_BG.code());
				}
				System.out.printf(format, (Object[]) row);
				if (invert) {
					System.out.print(Color.RESET.code());
				}
				invert = !invert;
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
}
