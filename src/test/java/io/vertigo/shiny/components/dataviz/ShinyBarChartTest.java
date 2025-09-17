package io.vertigo.shiny.components.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.dataviz.barchart.ShinyBarChartStyle;
import io.vertigo.shiny.components.dataviz.barchart.ShinySortMode;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyBarChartTest {
	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testSimpleBarChart(writer);
		testEuropeanCountriesPopulation(writer);
		testBarChartWithDifferentSortModes(writer);
	}

	private static void testSimpleBarChart(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Simple Bar Chart ---"));
		Shiny.barChart()
				.withTitle("Monthly Sales")
				.withHeader("Jan", "Feb", "Mar", "Apr", "May")
				.withValues(100, 120, 90, 150, 110)
				.render(writer); // Max bar length of 50 characters
		writer.println();
	}

	private static void testEuropeanCountriesPopulation(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- European Countries Population (Sorted Desc) ---"));
		Shiny.barChart()
				.withTitle("Population in European Countries")
				.withHeader("Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus",
						"Czech Republic", "Denmark", "Estonia", "Finland", "France",
						"Germany", "Greece", "Hungary", "Ireland", "Italy",
						"Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands",
						"Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden")
				.withValues(9000000, 11700000, 6400000, 3900000, 920000,
						10900000, 5900000, 1300000, 5600000, 68000000,
						84000000, 10200000, 9600000, 5300000, 59000000,
						1800000, 2800000, 660000, 520000, 17800000,
						37600000, 10300000, 19000000, 5400000, 2100000, 48000000, 10600000)
				.withSort(ShinySortMode.VALUE_DESC)
				.render(writer);
		writer.println();
	}

	private static void testBarChartWithDifferentSortModes(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Bar Chart with Different Sort Modes ---"));
		// Original data
		final String[] headers = { "Apple", "Banana", "Cherry", "Date" };
		final int[] values = { 50, 20, 80, 30 };

		// No sort
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- No Sort ---"));
		Shiny.barChart()
				.withTitle("Fruits (No Sort)")
				.withHeader(headers)
				.withValues(values)
				.withSort(ShinySortMode.NO)
				.render(writer);
		writer.println();

		// Value Ascending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Value Ascending ---"));
		Shiny.barChart()
				.withTitle("Fruits (Value Asc)")
				.withHeader(headers)
				.withValues(values)
				.withSort(ShinySortMode.VALUE_ASC)
				.render(writer);
		writer.println();

		// Value Descending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Value Descending ---"));
		Shiny.barChart()
				.withTitle("Fruits (Value Desc)")
				.withHeader(headers)
				.withValues(values)
				.withSort(ShinySortMode.VALUE_DESC)
				.render(writer);
		writer.println();

		// Header Ascending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Header Ascending ---"));
		Shiny.barChart()
				.withTitle("Fruits (Header Asc)")
				.withHeader(headers)
				.withValues(values)
				.withSort(ShinySortMode.HEADER_ASC)
				.render(writer);
		writer.println();

		// Header Descending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Header Descending ---"));
		Shiny.barChart()
				.withTitle("Fruits (Header Desc)")
				.withHeader(headers)
				.withValues(values)
				.withSort(ShinySortMode.HEADER_DESC)
				.withStyle(new ShinyBarChartStyle()
						.withColors(ShinyColors.MAGENTA, ShinyColors.MAGENTA_BRIGHT))
				.render(writer);
		writer.println();
	}
}