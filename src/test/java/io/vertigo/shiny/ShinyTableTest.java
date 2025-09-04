package io.vertigo.shiny;

import java.util.List;

import io.vertigo.shiny.color.ShinyColors;
import io.vertigo.shiny.data.table.ShinyBorder;
import io.vertigo.shiny.data.table.ShinyTable;

public class ShinyTableTest {

	public static void main(final String[] args) {
		testBasicTable();
		testEuropeanCountriesTable();
		testEmptyTable();
		testTableWithCustomStyles();
	}

	private static void testBasicTable() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Basic Table ---" + ShinyColors.RESET);
		final List<String[]> data = List.of(
				new String[] { "John Doe", "30", "New York" },
				new String[] { "Jane Smith", "25", "London" },
				new String[] { "Peter Jones", "35", "Paris" });

		Shiny.table()
				.title("User Information")
				.header("Name", "Age", "City")
				.rows(data)
				.print();
		System.out.println();
	}

	private static void testEuropeanCountriesTable() {
		System.out.println(ShinyColors.BLUE.bright() + "--- European Union Countries - Population ---" + ShinyColors.RESET);
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
				.print();
		System.out.println();
	}

	private static void testEmptyTable() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Empty Table ---" + ShinyColors.RESET);
		Shiny.table()
				.title("Empty Data")
				.header("Col1", "Col2")
				.rows(List.of()) // Empty data
				.noDataFound("No data available for this table.")
				.print();
		System.out.println();
	}

	private static void testTableWithCustomStyles() {
		System.out.println(ShinyColors.BLUE.bright() + "--- Table with Custom Styles ---" + ShinyColors.RESET);
		final List<String[]> data = List.of(
				new String[] { "Item A", "100", "Active" },
				new String[] { "Item B", "250", "Inactive" },
				new String[] { "Item C", "75", "Active" });

		Shiny.table()
				.title("Product Status")
				.header("Product", "Quantity", "Status")
				.rows(data)
				.beginStyle()
				.titleBackgroundColor(ShinyColors.WHITE)
				.headerBackgroundColor(ShinyColors.GREEN.bright())
				.altRowBackgroundColor(ShinyColors.CYAN.bright())
				.borderColor(ShinyColors.RED)
				.endStyle()
				.print();
		System.out.println();

		final ShinyTable table = Shiny.table()
				.title("Sales Report")
				.header("Region", "Sales", "Growth")
				.rows(List.of(
						new String[] { "North", "12345", "10.5%" },
						new String[] { "South", "9876", "5.2%" }))
				.beginStyle()
				.titleBackgroundColor(ShinyColors.BLUE)
				.headerBackgroundColor(ShinyColors.WHITE)
				.altRowBackgroundColor(ShinyColors.CYAN)
				.borderColor(ShinyColors.YELLOW)
				.endStyle();

		table
				.title("Sales Report - Simple")
				.beginStyle()
				.border(ShinyBorder.Simple)
				.endStyle()
				.print();
		System.out.println();

		table
				.title("Sales Report - SimpleHeavy")
				.beginStyle()
				.border(ShinyBorder.SimpleHeavy)
				.endStyle()
				.print();
		System.out.println();

		table
				.title("Sales Report - Horizontal")
				.beginStyle()
				.border(ShinyBorder.Horizontal)
				.endStyle()
				.print();
		System.out.println();
	}
}
