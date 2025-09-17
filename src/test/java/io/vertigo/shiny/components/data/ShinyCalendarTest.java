package io.vertigo.shiny.components.data;

import java.time.LocalDate;
import java.time.Month;
import java.util.Locale;

import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.style.ShinyColors;

public class ShinyCalendarTest {

	public static void main(final String[] args) {
		final ShinyWriter writer = Shiny.writer();

		testCurrentMonth(writer);
		testSpecificMonthAndYear(writer);
		testHighlightedDates(writer);
		testDifferentLocales(writer);
		testBordersAndHighlightColors(writer);
	}

	private static void testCurrentMonth(ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Current Month Calendar ---"));
		Shiny.calendar()
				.render(writer);
		writer.println();
	}

	private static void testSpecificMonthAndYear(ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Specific Month and Year Calendar (July 2024) ---"));
		Shiny.calendar()
				.withYear(2024)
				.withMonth(Month.JULY)
				.render(writer);
		writer.println();

		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Specific Month and Year Calendar (December 2023) ---"));
		Shiny.calendar()
				.withYear(2023)
				.withMonth(Month.DECEMBER)
				.render(writer);
		writer.println();
	}

	private static void testHighlightedDates(ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with Highlighted Dates (August 2024) ---"));
		Shiny.calendar()
				.withYear(2024)
				.withMonth(Month.AUGUST)
				.addHighlightedDate(LocalDate.of(2024, Month.AUGUST, 15)) // Highlight a specific date
				.addHighlightedDate(LocalDate.of(2024, Month.AUGUST, 20)) // Highlight another date
				.render(writer);
		writer.println();
	}

	private static void testDifferentLocales(ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with French Locale (September 2024) ---"));
		Shiny.theme().locale(Locale.FRENCH);
		Shiny.calendar()
				.withYear(2024)
				.withMonth(Month.SEPTEMBER)
				.addHighlightedDate(LocalDate.of(2024, Month.SEPTEMBER, 1)) // Highlight a date in French calendar
				.render(writer);
		writer.println();

		System.out.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with German Locale (January 2025) ---"));
		Shiny.theme().locale(Locale.GERMAN);
		Shiny.calendar()
				.withYear(2025)
				.withMonth(Month.JANUARY)
				.addHighlightedDate(LocalDate.of(2025, Month.JANUARY, 1))
				.render(writer);
		writer.println();
	}

	private static void testBordersAndHighlightColors(ShinyWriter writer) {
		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with SQUARE Border and Custom Highlight Color (October 2024) ---"));
		Shiny.calendar()
				.withYear(2024)
				.withMonth(Month.OCTOBER)
				.addHighlightedDate(LocalDate.of(2024, Month.OCTOBER, 10))
				.addHighlightedDate(LocalDate.of(2024, Month.OCTOBER, 20))
				//.highlightColor(ShinyColors.MAGENTA_BRIGHT) // Custom highlight color
				.render(writer);
		writer.println();

		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with ROUNDED Border (November 2024) ---"));
		Shiny.calendar()
				.withYear(2024)
				.withMonth(Month.NOVEMBER.getValue())
				.render(writer);
		writer.println();

		writer.println(ShinyColors.BLUE_BRIGHT.fg("--- Calendar with ASCII Border (February 2025) ---"));
		Shiny.theme().ascii();
		Shiny.calendar()
				.withYear(2025)
				.withMonth(Month.FEBRUARY.getValue())
				.render(writer);
		writer.println();
	}
}