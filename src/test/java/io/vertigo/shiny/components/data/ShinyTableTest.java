package io.vertigo.shiny.components.data;

import java.util.List;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.data.table.ShinyBorder;
import io.vertigo.shiny.components.data.table.ShinyTable;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyTableTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();
		testBasicTable(writer);
		testEuropeanCountriesTable(writer);
		testEmptyTable(writer);
		testTableWithCustomStyles(writer);
	}

	private static void testBasicTable(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Basic Table ---"));
		final List<String[]> data = List.of(
				new String[] { "John Doe", "30", "New York" },
				new String[] { "Jane Smith", "25", "London" },
				new String[] { "Peter Jones", "35", "Paris" });

		Shiny.table()
				.title("User Information")
				.header("Name", "Age", "City")
				.rows(data)
				.render(writer);
		writer.println();
	}

	private static void testEuropeanCountriesTable(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- European Union Countries - Population ---"));
		final List<String[]> euCountries = List.of(
				new String[] { "Austria", "9000000" },
				new String[] { "Belgium", "11700000" },
				new String[] { "Bulgaria", "6400000" },
				new String[] { "Croatia", "3900000" },
				new String[] { "Cyprus", "920000" },
				new String[] { "Czech Republic", "10900000" },
				new String[] { "Denmark", "5900000" },
				new String[] { "Estonia", "1300000" },
				new String[] { "Finland", "5600000" },
				new String[] { "France", "68000000" },
				new String[] { "Germany", "84000000" },
				new String[] { "Greece", "10200000" },
				new String[] { "Hungary", "9600000" },
				new String[] { "Ireland", "5300000" },
				new String[] { "Italy", "59000000" },
				new String[] { "Latvia", "1800000" },
				new String[] { "Lithuania", "2800000" },
				new String[] { "Luxembourg", "660000" },
				new String[] { "Malta", "520000" },
				new String[] { "Netherlands", "17800000" },
				new String[] { "Poland", "37600000" },
				new String[] { "Portugal", "10300000" },
				new String[] { "Romania", "19000000" },
				new String[] { "Slovakia", "5400000" },
				new String[] { "Slovenia", "2100000" },
				new String[] { "Spain", "48000000" },
				new String[] { "Sweden", "10600000" });

		Shiny.table()
				.title("European Union Countries - Population")
				.header("Country", "Population")
				.rows(euCountries)
				.render(writer);
		writer.println();
	}

	private static void testEmptyTable(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Empty Table ---"));
		Shiny.table()
				.title("Empty Data")
				.header("Col1", "Col2")
				.rows(List.of()) // Empty data
				.noDataFound("No data available for this table.")
				.render(writer);
		writer.println();
	}

	private static void testTableWithCustomStyles(final ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Table with Custom Styles ---"));
		final List<String[]> data = List.of(
				new String[] { "Item A", "100", "Active" },
				new String[] { "Item B", "250", "Inactive" },
				new String[] { "Item C", "75", "Active" });

		Shiny.table()
				.title("Product Status")
				.header("Product", "Quantity", "Status")
				.rows(data)
				.style(new ShinyTableStyle()
						.titleBackgroundColor(ShinyColors.WHITE)
						.headerBackgroundColor(ShinyColors.GREEN_BRIGHT)
						.altRowBackgroundColor(ShinyColors.CYAN_BRIGHT)
						.borderColor(ShinyColors.RED))
				.render(writer);
		writer.println();

		final ShinyTable table = Shiny.table()
				.title("Sales Report")
				.header("Region", "Sales", "Growth")
				.rows(List.of(
						new String[] { "North", "12345", "10.5%" },
						new String[] { "South", "9876", "5.2%" }))
				.style(new ShinyTableStyle()
						.titleBackgroundColor(ShinyColors.BLUE)
						.headerBackgroundColor(ShinyColors.WHITE)
						.altRowBackgroundColor(ShinyColors.CYAN)
						.borderColor(ShinyColors.YELLOW));

		table
				.title("Sales Report - Simple")
				.style(new ShinyTableStyle()
						.border(ShinyBorder.Simple))
				.render(writer);
		writer.println();

		table
				.title("Sales Report - SimpleHeavy")
				.style(new ShinyTableStyle()
						.border(ShinyBorder.SimpleHeavy))
				.render(writer);
		writer.println();

		table
				.title("Sales Report - Horizontal")
				.style(new ShinyTableStyle()
						.border(ShinyBorder.Horizontal))
				.render(writer);
		writer.println();
	}
}
