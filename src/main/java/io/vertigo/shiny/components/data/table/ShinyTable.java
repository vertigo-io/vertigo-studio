package io.vertigo.shiny.components.data.table;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

/**
 * Represents a table that can be printed in a terminal with colors,
 * borders and formatted numeric values.
 */
public final class ShinyTable implements ShinyComponent {
	private String tableTitle;
	private String noDataFound;
	private String[] tableHeader;
	private final List<String[]> tableRows = new ArrayList<>();
	private ShinyTableStyle tableStyle;

	/**
	 * Creates a new ShinyTable.
	 *
	 * @param numberFormat the NumberFormat used to format numeric values
	 */
	public ShinyTable() {
		this.tableStyle = Shiny.theme().tableStyle();
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
	public ShinyTable style(ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.tableStyle = style;
		return this;
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
	public void render(ShinyWriter writer) {
		Assertion.check()
				//				.isNotBlank(tableTitle)
				.isNotNull(tableRows)
				.when(tableRows.isEmpty(), () -> Assertion.check().isNotBlank(noDataFound))
				.isNotNull(tableHeader);

		final NumberFormat numberFormat = Shiny.theme().numberFormat();

		if (tableRows.isEmpty()) {
			writer.println(noDataFound);
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
					.append(i == 0 ? "" : tableStyle.border().chars().vertical())
					.append(isNumericColumn[i] ? " %" : " %-")
					.append(widths[i]).append("s ");
		}
		final String format = formatBuilder.toString();

		// 4. Print
		if (tableTitle != null) {
			writer.println(tableStyle.titleBackgroundColor().bg(
					tableStyle.titleTextColor().fg(tableTitle)));
		}
		printLineSeparator(writer, widths, Position.TOP);

		writer.print(tableStyle.border().chars().vertical())
				.print(tableStyle.headerBackgroundColor().bg(String.format(format, (Object[]) tableHeader)))
				.println(tableStyle.border().chars().vertical());

		printLineSeparator(writer, widths, Position.INNER);

		boolean invert = false;
		for (final String[] formattedRow : formattedRows) {
			writer.print(tableStyle.border().chars().vertical());
			final String srow = String.format(format, (Object[]) formattedRow);
			if (invert) {
				writer.print(tableStyle.altRowBackgroundColor().bg(srow));
			} else {
				writer.print(srow);
			}
			writer.println(tableStyle.border().chars().vertical());
			invert = !invert;
		}
		printLineSeparator(writer, widths, Position.BOTTOM);
	}

	private static enum Position {
		TOP, INNER, BOTTOM
	}

	private void printLineSeparator(final ShinyWriter writer, final int[] widths, final Position position) {
		boolean first = true;
		for (final int width : widths) {
			if (first) {
				final var left = switch (position) {
					case TOP -> tableStyle.border().chars().topLeft();
					case INNER -> tableStyle.border().chars().innerLeft();
					case BOTTOM -> tableStyle.border().chars().bottomLeft();
				};
				writer.print(left);
				final var h = switch (position) {
					case TOP -> tableStyle.border().chars().topHorizontal();
					case INNER -> tableStyle.border().chars().innerHorizontal();
					case BOTTOM -> tableStyle.border().chars().bottomHorizontal();
				};
				writer.print(h.repeat(width + 2));
				first = false;
			} else {
				final var middle = switch (position) {
					case TOP -> tableStyle.border().chars().topMiddle();
					case INNER -> tableStyle.border().chars().center();
					case BOTTOM -> tableStyle.border().chars().bottomMiddle();
				};
				writer.print(middle);
				final var h = switch (position) {
					case TOP -> tableStyle.border().chars().topHorizontal();
					case INNER -> tableStyle.border().chars().innerHorizontal();
					case BOTTOM -> tableStyle.border().chars().bottomHorizontal();
				};
				writer.print(h.repeat(width + 2));
			}

		}
		final String right = switch (position) {
			case TOP -> tableStyle.border().chars().topRight();
			case INNER -> tableStyle.border().chars().innerRight();
			case BOTTOM -> tableStyle.border().chars().bottomRight();
		};
		writer.println(right);
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
