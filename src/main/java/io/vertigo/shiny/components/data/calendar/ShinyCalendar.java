package io.vertigo.shiny.components.data.calendar;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyCalendar(
		//private final List<LocalDate> highlightedDates;
		int year,
		int month) implements ShinyComponent {

	@Override
	public String type() {
		return "calendar";
	}
}
