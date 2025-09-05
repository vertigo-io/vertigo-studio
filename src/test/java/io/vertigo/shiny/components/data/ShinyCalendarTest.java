package io.vertigo.shiny.components.data;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyCalendarTest {

	public static void main(final String[] args) {
		testCurrentMonth();
		testSpecificMonthAndYear();
		testHighlightedDates();
		testDifferentLocales();
		testBordersAndHighlightColors();
	}

	private static void testCurrentMonth() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Current Month Calendar ---"));
		Shiny.calendar()
				.print();
		System.out.println();
	}

	private static void testSpecificMonthAndYear() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Specific Month and Year Calendar (July 2024) ---"));
		Shiny.calendar()
				.year(2024)
				.month(Month.JULY)
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Specific Month and Year Calendar (December 2023) ---"));
		Shiny.calendar()
				.year(2023)
				.month(Month.DECEMBER)
				.print();
		System.out.println();
	}

	private static void testHighlightedDates() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with Highlighted Dates (August 2024) ---"));
		Shiny.calendar()
				.year(2024)
				.month(Month.AUGUST)
				.highlight(LocalDate.of(2024, Month.AUGUST, 15)) // Highlight a specific date
				.highlight(LocalDate.of(2024, Month.AUGUST, 20)) // Highlight another date
				.print();
		System.out.println();
	}

	private static void testDifferentLocales() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with French Locale (September 2024) ---"));
		Shiny.calendar()
				.year(2024)
				.month(Month.SEPTEMBER)
				.locale(Locale.FRENCH) // Set locale to French
				.highlight(LocalDate.of(2024, Month.SEPTEMBER, 1)) // Highlight a date in French calendar
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with German Locale (January 2025) ---"));
		Shiny.calendar()
				.year(2025)
				.month(Month.JANUARY)
				.locale(Locale.GERMAN) // Set locale to German
				.highlight(LocalDate.of(2025, Month.JANUARY, 1))
				.print();
		System.out.println();
	}

	private static void testBordersAndHighlightColors() {
		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with SQUARE Border and Custom Highlight Color (October 2024) ---"));
		Shiny.calendar()
				.year(2024)
				.month(Month.OCTOBER)
				.highlight(LocalDate.of(2024, Month.OCTOBER, 10))
				.highlight(LocalDate.of(2024, Month.OCTOBER, 20))
				//.highlightColor(ShinyColors.MAGENTA_BRIGHT) // Custom highlight color
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with ROUNDED Border (November 2024) ---"));
		Shiny.calendar()
				.year(2024)
				.month(Month.NOVEMBER.getValue())
				.print();
		System.out.println();

		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with ASCII Border (February 2025) ---"));
		Shiny.calendar()
				.year(2025)
				.month(Month.FEBRUARY.getValue())
				.print();
		System.out.println();
	}
}
