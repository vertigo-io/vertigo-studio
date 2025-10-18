package io.vertigo.shiny.models.data.calendar;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyCalendar(
		//private final List<LocalDate> highlightedDates;
		int year,
		int month) implements ShinyModel {

	@Override
	public String shinyType() {
		return "calendar";
	}
}
