package io.vertigo.shell.shiny;

import java.time.Month;
import java.util.Locale;

import io.vertigo.shell.shiny.table.ShinyBorder;

public class ShinyCalendarTest {

	public static void main(final String[] args) {
		test();
	}

	private static void test() {
		// Current month
		Shiny.calendar()
				.print();

		// Specific month and year
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY)
				.print();

		Shiny.calendar()
				.year(2024)
				.month(7) //<- by int
				.print();

		// With highlighted dates
		Shiny.calendar()
				.year(2024)
				.month(Month.AUGUST)
				//				.highlight(LocalDate.of(2024, Month.AUGUST, 15))
				//				.highlight(LocalDate.of(2024, Month.AUGUST, 20))
				.print();

		// US locale
		Shiny.calendar()
				.year(2024)
				.month(Month.SEPTEMBER)
				.locale(Locale.US)
				//						.highlight(LocalDate.of(2024, Month.SEPTEMBER, 1))
				.print();

		// With SQUARE border and custom highlight color
		Shiny.calendar()
				.year(2024)
				.month(Month.OCTOBER)
				.border(ShinyBorder.Square)
				//						.highlight(LocalDate.of(2024, Month.OCTOBER, 10))
				//						.highlight(LocalDate.of(2024, Month.OCTOBER, 20))
				//				.highlightColor(ShinyColors.MAGENTA_BRIGHT)
				.print();
		// With ROUNDED border
		Shiny.calendar()
				.year(2025)
				.month(Month.AUGUST)
				.border(ShinyBorder.Rounded)
				.print();
	}
}
