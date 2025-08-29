package io.vertigo.shell.shiny;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import io.vertigo.shell.shiny.table.ShinyBorder;
import io.vertigo.shell.shiny.utils.ShinyColors;

public class ShinyCalendarTest {

	public static void main(final String[] args) {
		testCurrentMonth();
		testSpecificMonthAndYear();
		testHighlightedDates();
		testDifferentLocales();
		testBordersAndHighlightColors();
	}

	private static void testCurrentMonth() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Current Month Calendar ---" + ShinyColors.RESET);
		Shiny.calendar()
				.print();
		System.out.println();
	}

	private static void testSpecificMonthAndYear() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Specific Month and Year Calendar (July 2024) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY)
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Specific Month and Year Calendar (December 2023) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2023)
				.month(Month.DECEMBER)
				.print();
		System.out.println();
	}

	private static void testHighlightedDates() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with Highlighted Dates (August 2024) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2024)
				.month(Month.AUGUST)
				.highlight(LocalDate.of(2024, Month.AUGUST, 15)) // Highlight a specific date
				.highlight(LocalDate.of(2024, Month.AUGUST, 20)) // Highlight another date
				.print();
		System.out.println();
	}

	private static void testDifferentLocales() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with French Locale (September 2024) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2024)
				.month(Month.SEPTEMBER)
				.locale(Locale.FRENCH) // Set locale to French
				.highlight(LocalDate.of(2024, Month.SEPTEMBER, 1)) // Highlight a date in French calendar
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with German Locale (January 2025) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2025)
				.month(Month.JANUARY)
				.locale(Locale.GERMAN) // Set locale to German
				.highlight(LocalDate.of(2025, Month.JANUARY, 1))
				.print();
		System.out.println();
	}

	private static void testBordersAndHighlightColors() {
		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with SQUARE Border and Custom Highlight Color (October 2024) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2024)
				.month(Month.OCTOBER)
				.border(ShinyBorder.Square) // Set SQUARE border
				.highlight(LocalDate.of(2024, Month.OCTOBER, 10))
				.highlight(LocalDate.of(2024, Month.OCTOBER, 20))
				//.highlightColor(ShinyColors.MAGENTA_BRIGHT) // Custom highlight color
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with ROUNDED Border (November 2024) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2024)
				.month(Month.NOVEMBER.getValue())
				.border(ShinyBorder.Rounded) // Set ROUNDED border
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT + "--- Calendar with ASCII Border (February 2025) ---" + ShinyColors.RESET);
		Shiny.calendar()
				.year(2025)
				.month(Month.FEBRUARY.getValue())
				.border(ShinyBorder.Ascii) // Set ASCII border
				.print();
		System.out.println();
	}
}
