package io.vertigo.shiny.components.data.calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public record ShinyCalendar(
		//private final List<LocalDate> highlightedDates;
		int year,
		int month,
		@JsonIgnore ShinyTableStyle style) implements ShinyComponent {
}
