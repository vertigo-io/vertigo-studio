package io.vertigo.shell.shiny.table;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shell.shiny.Shiny;
import io.vertigo.shell.shiny.utils.ShinyColors;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 */
public final class ShinyTable {
	private final NumberFormat numberFormat;
	private String title;
	private String noDataFound;
	private String[] header;
	private final List<String[]> rows = new ArrayList<>();
	private final ShinyTableStyle style = new ShinyTableStyle(this);

	/**
	 * Creates a new ShinyTable.
	 *
	 * @param numberFormat the NumberFormat used to format numeric values
	 */
	public ShinyTable(Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.numberFormat = shiny.getNumberFormat();
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
	 * Opens Style.
	 */
	public ShinyTableStyle beginStyle() {
		return style;
	}

	/**
	 * Sets the message to be displayed when no data is found.
	 */
	public ShinyTable noDataFound(String noDataFound) {
		this.noDataFound = noDataFound;
		return this;
	}

	/**
	 * Defines the header row of the table.
	 */
	public ShinyTable header(String... header) {
		this.header = header;
		return this;
	}

	/**
	 * Adds multiple rows of data to the table.
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

		if (rows.isEmpty()) {
			System.out.println(noDataFound);
			return;
		}

		int columns = header.length;

		// 1. Format data and detect numeric columns
		List<String[]> formattedRows = new ArrayList<>();
		boolean[] isNumericColumn = new boolean[columns];

		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(rows, i);
		}

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

		// 2. Compute max width per column
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

		// 3. Build format string
		StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < columns; i++) {
			formatBuilder
					.append(style.border.chars().vertical())
					.append(isNumericColumn[i] ? " %" : " %-")
					.append(widths[i]).append("s ");
		}
		formatBuilder
				.append(style.border.chars().vertical())
				.append("\n");
		final String format = formatBuilder.toString();

		// 4. Print
		System.out.print(style.titleBackgroundColor);
		System.out.print(style.titleTextColor);
		System.out.println(title);
		System.out.print(ShinyColors.RESET);

		printLineSeparator(widths, Position.TOP);

		System.out.print(style.headerBackgroundColor);
		System.out.printf(format, (Object[]) header);
		System.out.print(ShinyColors.RESET);

		printLineSeparator(widths, Position.INNER);

		boolean invert = false;
		for (String[] formattedRow : formattedRows) {
			if (invert) {
				System.out.print(style.altRowBackgroundColor);
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
		TOP, INNER, BOTTOM
	}

	private void printLineSeparator(int[] widths, Position position) {
		boolean first = true;
		for (int width : widths) {
			if (first) {
				var left = switch (position) {
					case TOP -> style.border.chars().topLeft();
					case INNER -> style.border.chars().innerLeft();
					case BOTTOM -> style.border.chars().bottomLeft();
				};
				System.out.print(left);
				var h = switch (position) {
					case TOP -> style.border.chars().topHorizontal();
					case INNER -> style.border.chars().innerHorizontal();
					case BOTTOM -> style.border.chars().bottomHorizontal();
				};
				System.out.print(h.repeat(width + 2));
				first = false;
			} else {
				var middle = switch (position) {
					case TOP -> style.border.chars().topMiddle();
					case INNER -> style.border.chars().center();
					case BOTTOM -> style.border.chars().bottomMiddle();
				};
				System.out.print(middle);
				var h = switch (position) {
					case TOP -> style.border.chars().topHorizontal();
					case INNER -> style.border.chars().innerHorizontal();
					case BOTTOM -> style.border.chars().bottomHorizontal();
				};
				System.out.print(h.repeat(width + 2));
			}

		}
		String right = switch (position) {
			case TOP -> style.border.chars().topRight();
			case BOTTOM -> style.border.chars().bottomRight();
			case INNER -> style.border.chars().innerRight();
		};
		System.out.println(right);
	}

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

		return totalNonNullValues > 0 && (double) numericCount / totalNonNullValues >= 0.8;
	}

	private static boolean isNumeric(String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}

		String trimmed = str.trim();
		try {
			Double.parseDouble(trimmed.replace(",", ".").replace(" ", ""));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static String formatNumber(String str, NumberFormat numberFormat) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}

		try {
			String cleaned = str.trim().replace(",", ".").replace(" ", "");
			double number = Double.parseDouble(cleaned);

			if (number == Math.floor(number) && !Double.isInfinite(number)) {
				return numberFormat.format((long) number);
			} else {
				return numberFormat.format(number);
			}
		} catch (NumberFormatException e) {
			return str;
		}
	}

}
