package io.vertigo.shiny.models.dataviz;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
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
		Shiny.render(
				Shiny.barChart()
						.withTitle("Monthly Sales")
						.withLabels("Jan", "Feb", "Mar", "Apr", "May")
						.addSerie("sales", 100d, 120d, 90d, 150d, 110d)
						.build()); // Max bar length of 50 characters
		writer.println();
	}

	private static void testEuropeanCountriesPopulation(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- European Countries Population (Sorted Desc) ---"));
		Shiny.render(
				Shiny.barChart()
						.withTitle("Population in European Countries")
						.withLabels("Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus",
								"Czech Republic", "Denmark", "Estonia", "Finland", "France",
								"Germany", "Greece", "Hungary", "Ireland", "Italy",
								"Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands",
								"Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden")
						.addSerie("population", 9000000d, 11700000d, 6400000d, 3900000d, 920000d,
								10900000d, 5900000d, 1300000d, 5600000d, 68000000d,
								37600000d, 10300000d, 19000000d, 5400000d, 2100000d, 48000000d, 10600000d)
						.build());
		writer.println();
	}

	private static void testBarChartWithDifferentSortModes(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Bar Chart with Different Sort Modes ---"));
		// Original data
		final String[] headers = { "Apple", "Banana", "Cherry", "Date" };
		final Double[] values = { 50d, 20d, 80d, 30d };

		// No sort
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- No Sort ---"));
		Shiny.render(
				Shiny.barChart()
						.withTitle("Fruits (No Sort)")
						.withLabels(headers)
						.addSerie("", values)
						.build());
		writer.println();

		// Value Ascending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Value Ascending ---"));
		Shiny.render(
				Shiny.barChart()
						.withTitle("Fruits (Value Asc)")
						.withLabels(headers)
						.addSerie("", values)
						//.withSort(ShinySortMode.VALUE_ASC)
						.build());
		writer.println();

		// Value Descending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Value Descending ---"));
		Shiny.render(
				Shiny.barChart()
						.withTitle("Fruits (Value Desc)")
						.withLabels(headers)
						.addSerie("", values)
						//.withSort(ShinySortMode.VALUE_DESC)
						.build());
		writer.println();

		// Header Ascending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Header Ascending ---"));
		Shiny.render(
				Shiny.barChart()
						.withTitle("Fruits (Header Asc)")
						.withLabels(headers)
						.addSerie("", values)
						//.withSort(ShinySortMode.HEADER_ASC)
						.build());
		writer.println();

		// Header Descending
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Header Descending ---"));

		Shiny.theme().barChartStyle()
				.withColors(ShinyColors.MAGENTA, ShinyColors.MAGENTA_BRIGHT);

		Shiny.render(
				Shiny.barChart()
						.withTitle("Fruits (Header Desc)")
						.withLabels(headers)
						.addSerie("", values)
						//.withSort(ShinySortMode.HEADER_DESC)
						.build());
		writer.println();
	}
}
