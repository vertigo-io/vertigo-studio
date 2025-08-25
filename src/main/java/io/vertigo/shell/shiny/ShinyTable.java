package io.vertigo.shell.shiny;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 *
 * Features:
 * - Customizable title and header
 * - Optional borders
 * - Automatic detection and formatting of numeric columns
 * - Alternate row coloring for readability
 * - Support for displaying a message when no data is found
 */
public final class ShinyTable {
	private final NumberFormat numberFormat;
	private String title;
	private String noDataFound;
	private String[] header;
	private boolean border = true;
	private final List<String[]> rows = new ArrayList<>();

	/**
	 * Creates a new ShinyTable.
	 *
	 * @param numberFormat the NumberFormat used to format numeric values
	 */
	ShinyTable(NumberFormat numberFormat) {
		Assertion.check().isNotNull(numberFormat);
		//---
		this.numberFormat = numberFormat;
	}

	/**
	 * Sets the title of the table.
	 *
	 * @param title the table title
	 * @return this instance for method chaining
	 */
	public ShinyTable title(String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.title = title;
		return this;
	}

	/**
	 * Enables borders around the table.
	 *
	 * @return this instance for method chaining
	 */
	public ShinyTable border() {
		this.border = true;
		return this;
	}

	/**
	 * Disables borders around the table.
	 *
	 * @return this instance for method chaining
	 */
	public ShinyTable noBorder() {
		this.border = false;
		return this;
	}

	/**
	 * Sets the message to be displayed when no data is found.
	 *
	 * @param noDataFound the message to display
	 * @return this instance for method chaining
	 */
	public ShinyTable noDataFound(String noDataFound) {
		this.noDataFound = noDataFound;
		return this;
	}

	/**
	 * Defines the header row of the table.
	 *
	 * @param header the column names
	 * @return this instance for method chaining
	 */
	public ShinyTable header(String... header) {
		this.header = header;
		return this;
	}

	/**
	 * Adds multiple rows of data to the table.
	 *
	 * @param rows the list of row values
	 * @return this instance for method chaining
	 */
	public ShinyTable rows(List<String[]> rows) {
		this.rows.addAll(rows);
		return this;
	}

	/**
	 * Prints the table to the console with the configured formatting.
	 */
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

		// 1. Format data and detect numeric columns
		List<String[]> formattedRows = new ArrayList<>();
		boolean[] isNumericColumn = new boolean[columns];

		// Detect numeric columns
		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(rows, i);
		}

		// Format data
		for (String[] row : rows) {
			final String[] formattedRow = new String[columns];
			for (int colIndex = 0; colIndex < columns; colIndex++) {
				String value = row[colIndex];
				if (value != null && isNumericColumn[colIndex] && isNumeric(value)) {
					formattedRow[colIndex] = formatNumber(value, numberFormat);
				} else {
					formattedRow[colIndex] = value != null ? value : "";
				}
			}
			formattedRows.add(formattedRow);
		}

		// 2. Compute maximum width per column (header & formatted data)
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

		// 3. Build format string for each column with proper alignment
		StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < columns; i++) {
			formatBuilder
					.append(border ? ShinyChars.VERTICAL : " ")
					// Right alignment for numeric columns
					// Left alignment for text columns
					.append(isNumericColumn[i] ? " %" : " %-")
					.append(widths[i]).append("s ");
		}
		formatBuilder
				.append(border ? ShinyChars.VERTICAL : " ")
				.append("\n");
		String format = formatBuilder.toString();

		// 4. Generate horizontal separator

		// 5. Print the table
		System.out.print(ShinyColors.MAGENTA_BG);
		System.out.print(ShinyColors.WHITE);
		System.out.println(title);
		System.out.print(ShinyColors.RESET);
		printLineSeparator(widths, Position.TOP);
		System.out.print(ShinyColors.BLUE_BRIGHT_BG);
		System.out.printf(format, (Object[]) header);
		System.out.print(ShinyColors.RESET);
		printLineSeparator(widths, Position.MIDDLE);
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
		printLineSeparator(widths, Position.BOTTOM);
	}

	private static enum Position {
		TOP, MIDDLE, BOTTOM
	}

	private void printLineSeparator(int[] widths, Position position) {
		if (border) {
			printLineSeparatorWithBorder(widths, position);
		} else {
			printLineSeparatorNoBorder(widths);
		}
	}

	private static void printLineSeparatorWithBorder(int[] widths, Position position) {
		boolean first = true;
		for (int width : widths) {
			final String left;
			// Depending on the separator position, we need a corner or a T junction
			if (first) {
				left = switch (position) {
					case TOP -> ShinyChars.TOP_LEFT;
					case BOTTOM -> ShinyChars.BOTTOM_LEFT;
					case MIDDLE -> ShinyChars.T_RIGHT;
				};
				first = false;
			} else {
				left = switch (position) {
					case TOP -> ShinyChars.T_BOTTOM;
					case BOTTOM -> ShinyChars.T_TOP;
					case MIDDLE -> ShinyChars.CROSS;
				};

			}
			System.out.print(left);
			for (int i = 0; i < width + 2; i++)
				System.out.print(ShinyChars.HORIZONTAL);
		}
		String right = switch (position) {
			case TOP -> ShinyChars.TOP_RIGHT;
			case BOTTOM -> ShinyChars.BOTTOM_RIGHT;
			case MIDDLE -> ShinyChars.T_LEFT;
		};
		System.out.println(right);
	}

	private static void printLineSeparatorNoBorder(int[] widths) {
		System.out.print(ShinyColors.BLUE);
		//--
		for (int width : widths) {
			System.out.print(ShinyChars.HORIZONTAL);
			for (int i = 0; i < width + 2; i++)
				System.out.print(ShinyChars.HORIZONTAL);
		}
		System.out.print(ShinyChars.HORIZONTAL);
		System.out.println(ShinyColors.RESET);
	}

	/**
	 * Determines whether a column contains mostly numeric values.
	 *
	 * @param rows        the table rows
	 * @param columnIndex the column index
	 * @return true if at least 80% of the values are numeric
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

		// Consider the column numeric if at least 80% of values are numeric
		return totalNonNullValues > 0 && (double) numericCount / totalNonNullValues >= 0.8;
	}

	/**
	 * Checks if a string represents a number.
	 *
	 * @param str the string to test
	 * @return true if the string is numeric
	 */
	private static boolean isNumeric(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}

		String trimmed = str.trim();
		try {
			// Handles integers and decimals
			Double.parseDouble(trimmed.replace(",", ".").replace(" ", ""));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * Formats a numeric string using the provided NumberFormat.
	 * Thousands separators are applied, and integers are displayed without decimals.
	 *
	 * @param str          the string representing a number
	 * @param numberFormat the number format to use
	 * @return the formatted number, or the original string if parsing fails
	 */
	private static String formatNumber(String str, NumberFormat numberFormat) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}

		try {
			String cleaned = str.trim().replace(",", ".").replace(" ", "");
			double number = Double.parseDouble(cleaned);

			// If it is an integer, display without decimals
			if (number == Math.floor(number) && !Double.isInfinite(number)) {
				return numberFormat.format((long) number);
			} else {
				return numberFormat.format(number);
			}
		} catch (NumberFormatException e) {
			return str; // Return original value if formatting fails
		}
	}
}
