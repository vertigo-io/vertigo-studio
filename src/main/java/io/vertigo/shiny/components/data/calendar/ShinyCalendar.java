package io.vertigo.shiny.components.data.calendar;

import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public record ShinyCalendar(
		//private final List<LocalDate> highlightedDates;
		int year,
		int month,
		ShinyTableStyle style) implements ShinyComponent {
}
