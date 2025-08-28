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
	private final Shiny shiny;
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
	public ShinyTable(final Shiny shiny) {
		Assertion.check().isNotNull(shiny);
		//---
		this.shiny = shiny;
		this.numberFormat = shiny.getNumberFormat();
	}

	/**
	 * Sets the title of the table.
	 *
	 * @param title the table title
	 * @return this instance for method chaining
	 */
	public ShinyTable title(final String text) {
		Assertion.check().isNotBlank(text);
		//---
		this.title = text;
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
	public ShinyTable noDataFound(final String message) {
		this.noDataFound = message;
		return this;
	}

	/**
	 * Defines the header row of the table.
	 */
	public ShinyTable header(final String... texts) {
		this.header = texts;
		return this;
	}

	/**
	 * Adds multiple rows of data to the table.
	 */
	public ShinyTable rows(final List<String[]> list) {
		this.rows.addAll(list);
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
			shiny.getWriter().println(noDataFound);
			return;
		}

		final int columns = header.length;

		// 1. Format data and detect numeric columns
		final List<String[]> formattedRows = new ArrayList<>();
		final boolean[] isNumericColumn = new boolean[columns];

		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(rows, i);
		}

		for (final String[] row : rows) {
			Assertion.check().isTrue(row.length == columns, "Header and rows must have the same length");
			final String[] formattedRow = new String[columns];
			for (int colIndex = 0; colIndex < columns; colIndex++) {
				final String value = row[colIndex];
				if (value != null && isNumericColumn[colIndex] && isNumeric(value)) {
					formattedRow[colIndex] = formatNumber(value, numberFormat);
				} else {
					formattedRow[colIndex] = value != null ? value : "";
				}
			}
			formattedRows.add(formattedRow);
		}

		// 2. Compute max width per column
		final int[] widths = new int[columns];
		for (int i = 0; i < columns; i++) {
			widths[i] = header[i].length();
		}
		for (final String[] row : formattedRows) {
			for (int i = 0; i < columns; i++) {
				if (row[i] != null && row[i].length() > widths[i]) {
					widths[i] = row[i].length();
				}
			}
		}

		// 3. Build format string
		final StringBuilder formatBuilder = new StringBuilder();
		for (int i = 0; i < columns; i++) {
			formatBuilder
					.append(i == 0 ? "" : style.border.chars().vertical())
					.append(isNumericColumn[i] ? " %" : " %-")
					.append(widths[i]).append("s ");
		}
		final String format = formatBuilder.toString();

		// 4. Print
		shiny.getWriter().print(style.titleBackgroundColor);
		shiny.getWriter().print(style.titleTextColor);
		shiny.getWriter().println(title);
		shiny.getWriter().print(ShinyColors.RESET);

		printLineSeparator(widths, Position.TOP);

		shiny.getWriter().print(style.border.chars().vertical());
		shiny.getWriter().print(style.headerBackgroundColor);
		shiny.getWriter().printf(format, (Object[]) header);
		shiny.getWriter().print(ShinyColors.RESET);
		shiny.getWriter().println(style.border.chars().vertical());

		printLineSeparator(widths, Position.INNER);

		boolean invert = false;
		for (final String[] formattedRow : formattedRows) {
			shiny.getWriter().print(style.border.chars().vertical());
			if (invert) {
				shiny.getWriter().print(style.altRowBackgroundColor);
			}
			shiny.getWriter().printf(format, (Object[]) formattedRow);
			if (invert) {
				shiny.getWriter().print(ShinyColors.RESET);
			}
			shiny.getWriter().println(style.border.chars().vertical());
			invert = !invert;
		}
		printLineSeparator(widths, Position.BOTTOM);
	}

	private static enum Position {
		TOP, INNER, BOTTOM
	}

	private void printLineSeparator(final int[] widths, final Position position) {
		boolean first = true;
		for (final int width : widths) {
			if (first) {
				final var left = switch (position) {
					case TOP -> style.border.chars().topLeft();
					case INNER -> style.border.chars().innerLeft();
					case BOTTOM -> style.border.chars().bottomLeft();
				};
				shiny.getWriter().print(left);
				final var h = switch (position) {
					case TOP -> style.border.chars().topHorizontal();
					case INNER -> style.border.chars().innerHorizontal();
					case BOTTOM -> style.border.chars().bottomHorizontal();
				};
				shiny.getWriter().print(h.repeat(width + 2));
				first = false;
			} else {
				final var middle = switch (position) {
					case TOP -> style.border.chars().topMiddle();
					case INNER -> style.border.chars().center();
					case BOTTOM -> style.border.chars().bottomMiddle();
				};
				shiny.getWriter().print(middle);
				final var h = switch (position) {
					case TOP -> style.border.chars().topHorizontal();
					case INNER -> style.border.chars().innerHorizontal();
					case BOTTOM -> style.border.chars().bottomHorizontal();
				};
				shiny.getWriter().print(h.repeat(width + 2));
			}

		}
		final String right = switch (position) {
			case TOP -> style.border.chars().topRight();
			case INNER -> style.border.chars().innerRight();
			case BOTTOM -> style.border.chars().bottomRight();
		};
		shiny.getWriter().println(right);
	}

	private static boolean isColumnNumeric(final List<String[]> rows, final int columnIndex) {
		int numericCount = 0;
		int totalNonNullValues = 0;

		for (final String[] row : rows) {
			if (columnIndex < row.length && row[columnIndex] != null && !row[columnIndex].trim().isEmpty()) {
				totalNonNullValues++;
				if (isNumeric(row[columnIndex])) {
					numericCount++;
				}
			}
		}

		return totalNonNullValues > 0 && (double) numericCount / totalNonNullValues >= 0.8;
	}

	private static boolean isNumeric(final String str) {
		if (str == null || str.trim().isEmpty()) {
			return false;
		}

		final String trimmed = str.trim();
		try {
			Double.parseDouble(trimmed.replace(",", ".").replace(" ", ""));
			return true;
		} catch (final NumberFormatException e) {
			return false;
		}
	}

	private static String formatNumber(final String str, final NumberFormat numberFormat) {
		if (str == null || str.trim().isEmpty()) {
			return "";
		}

		try {
			final String cleaned = str.trim().replace(",", ".").replace(" ", "");
			final double number = Double.parseDouble(cleaned);

			if (number == Math.floor(number) && !Double.isInfinite(number)) {
				return numberFormat.format((long) number);
			} else {
				return numberFormat.format(number);
			}
		} catch (final NumberFormatException e) {
			return str;
		}
	}

}
