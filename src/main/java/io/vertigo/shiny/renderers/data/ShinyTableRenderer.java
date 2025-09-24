package io.vertigo.shiny.renderers.data;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyTableRenderer implements ShinyComponentRenderer<ShinyTable> {
	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyTable;
	}

	@Override
	public void render(final ShinyTable shinyTable) {
		Assertion.check()
				.isNotNull(shinyTable)
				.isNotNull(shinyTable.rows())
				.when(shinyTable.rows().isEmpty(), () -> Assertion.check().isNotBlank(shinyTable.noDataFound()))
				.isNotNull(shinyTable.header());
		//---
		final ShinyTableStyle style = Shiny.theme().tableStyle();
		final ShinyWriter writer = Shiny.writer();

		final NumberFormat numberFormat = Shiny.theme().numberFormat();

		if (shinyTable.rows().isEmpty()) {
			writer.println(shinyTable.noDataFound());
			return;
		}

		final int columns = shinyTable.header().length;

		// 1. Format data and detect numeric columns
		final List<String[]> formattedRows = new ArrayList<>();
		final boolean[] isNumericColumn = new boolean[columns];

		for (int i = 0; i < columns; i++) {
			isNumericColumn[i] = isColumnNumeric(shinyTable.rows(), i);
		}

		for (final String[] row : shinyTable.rows()) {
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

		// 2. Compute maxValue width per column
		final int[] widths = new int[columns];
		for (int i = 0; i < columns; i++) {
			widths[i] = shinyTable.header()[i].length();
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
					.append(i == 0 ? "" : style.border().chars().vertical())
					.append(isNumericColumn[i] ? " %" : " %-")
					.append(widths[i]).append("s ");
		}
		final String format = formatBuilder.toString();

		// 4. Print
		if (shinyTable.title() != null) {
			writer.println(style.titleBackgroundColor().bg(
					style.titleTextColor().fg(shinyTable.title())));
		}
		printLineSeparator(shinyTable, style, writer, widths, Position.TOP);

		writer.print(style.border().chars().vertical())
				.print(style.headerBackgroundColor().bg(String.format(format, (Object[]) shinyTable.header())))
				.println(style.border().chars().vertical());

		printLineSeparator(shinyTable, style, writer, widths, Position.INNER);

		boolean invert = false;
		for (final String[] formattedRow : formattedRows) {
			writer.print(style.border().chars().vertical());
			final String srow = String.format(format, (Object[]) formattedRow);
			if (invert) {
				writer.print(style.altRowBackgroundColor().bg(srow));
			} else {
				writer.print(srow);
			}
			writer.println(style.border().chars().vertical());
			invert = !invert;
		}
		printLineSeparator(shinyTable, style, writer, widths, Position.BOTTOM);
	}

	private static enum Position {
		TOP, INNER, BOTTOM
	}

	private static void printLineSeparator(final ShinyTable shinyTable, final ShinyTableStyle style, final ShinyWriter writer, final int[] widths, final Position position) {
		boolean first = true;
		for (final int width : widths) {
			if (first) {
				final var left = switch (position) {
					case TOP -> style.border().chars().topLeft();
					case INNER -> style.border().chars().innerLeft();
					case BOTTOM -> style.border().chars().bottomLeft();
				};
				writer.print(left);
				final var h = switch (position) {
					case TOP -> style.border().chars().topHorizontal();
					case INNER -> style.border().chars().innerHorizontal();
					case BOTTOM -> style.border().chars().bottomHorizontal();
				};
				writer.print(h.repeat(width + 2));
				first = false;
			} else {
				final var middle = switch (position) {
					case TOP -> style.border().chars().topMiddle();
					case INNER -> style.border().chars().center();
					case BOTTOM -> style.border().chars().bottomMiddle();
				};
				writer.print(middle);
				final var h = switch (position) {
					case TOP -> style.border().chars().topHorizontal();
					case INNER -> style.border().chars().innerHorizontal();
					case BOTTOM -> style.border().chars().bottomHorizontal();
				};
				writer.print(h.repeat(width + 2));
			}

		}
		final String right = switch (position) {
			case TOP -> style.border().chars().topRight();
			case INNER -> style.border().chars().innerRight();
			case BOTTOM -> style.border().chars().bottomRight();
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
