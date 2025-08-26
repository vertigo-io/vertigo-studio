package io.vertigo.shell.shiny;

import io.vertigo.shell.shiny.barchart.ShinySortMode;

public class ShinyBarChartTest {
	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		Shiny.barChart()
				.title("Population in European Countries")
				.header("Austria", "Belgium", "Bulgaria", "Croatia", "Cyprus",
						"Czech Republic", "Denmark", "Estonia", "Finland", "France",
						"Germany", "Greece", "Hungary", "Ireland", "Italy",
						"Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands",
						"Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden")
				.rows(9000000, 11700000, 6400000, 3900000, 920000,
						10900000, 5900000, 1300000, 5600000, 68000000,
						84000000, 10200000, 9600000, 5300000, 59000000,
						1800000, 2800000, 660000, 520000, 17800000,
						37600000, 10300000, 19000000, 5400000, 2100000, 48000000, 10600000)
				.sort(ShinySortMode.VALUE_DESC)
				.print(100);
	}
}
