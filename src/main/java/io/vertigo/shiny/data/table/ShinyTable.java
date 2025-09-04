package io.vertigo.shiny.data.table;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyComponent;
import io.vertigo.shiny.color.ShinyColors;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 */
public final class ShinyTable implements ShinyComponent {
	private final NumberFormat numberFormat;
	private final Shiny shiny;
	private String tableTitle;
	private String noDataFound;
	private String[] tableHeader;
	private final List<String[]> tableRows = new ArrayList<>();
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
		this.numberFormat = Shiny.theme().numberFormat();
	}

	/**
	 * Sets the title of the table.
	 *
	 * @param tableTitle the table title
	 * @return this instance for method chaining
	 */
	public ShinyTable title(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.tableTitle = title;
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
	public ShinyTable header(final String... header) {
		this.tableHeader = header;
		return this;
	}

	/**
	 * Adds multiple rows of data to the table.
	 */
	public ShinyTable rows(final List<String[]> rows) {
		this.tableRows.addAll(rows);
		return this;
	}

	/**
	 * Adds multiple rows of data to the table.
	 */
	public ShinyTable rows(final String[]... rows) {
		this.tableRows.addAll(List.of(rows));
		return this;
	}

	/**
	 * Prints the table to the console with the configured formatting.
	 */
	public void print() {
		Assertion.check()
				.isNotBlank(tableTitle)
				.isNotNull(tableRows)
				.when(tableRows.isEmpty(), () -> Assertion.check().isNotBlank(noDataFound))
				.isNotNull(tableHeader);

		if (tableRows.isEmpty()) {
			shiny.getWriter().println(noDataFound);
			return;
		}

		final int columns = tableHeader.length;

		// 1. Format data and detect numeric columns
		final List<String[]> formattedRows = new ArrayList<>();
		final boolean[] isNumericColumn = new boolean[columns];

		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(tableRows, i);
		}

		for (final String[] row : tableRows) {
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
			widths[i] = tableHeader[i].length();
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
		shiny.getWriter().print(style.titleBackgroundColor.bg());
		shiny.getWriter().print(style.titleTextColor);
		shiny.getWriter().print(tableTitle);
		shiny.getWriter().println(ShinyColors.RESET);

		printLineSeparator(widths, Position.TOP);

		shiny.getWriter().print(style.border.chars().vertical());
		shiny.getWriter().print(style.headerBackgroundColor.bg());
		shiny.getWriter().printf(format, (Object[]) tableHeader);
		shiny.getWriter().print(ShinyColors.RESET);
		shiny.getWriter().println(style.border.chars().vertical());

		printLineSeparator(widths, Position.INNER);

		boolean invert = false;
		for (final String[] formattedRow : formattedRows) {
			shiny.getWriter().print(style.border.chars().vertical());
			if (invert) {
				shiny.getWriter().print(style.altRowBackgroundColor.bg());
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
