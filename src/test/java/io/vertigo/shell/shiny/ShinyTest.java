package io.vertigo.shell.shiny;

import java.util.List;

public class ShinyTest {

	public static void main(String[] args) {

		printEUCountriesTable();
	}

	public static void printEUCountriesTable() {
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
	}
}
