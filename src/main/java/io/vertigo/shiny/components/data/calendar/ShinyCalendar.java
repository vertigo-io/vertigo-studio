package io.vertigo.shiny.components.data.calendar;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public record ShinyCalendar(
		//private final List<LocalDate> highlightedDates;
		int year,
		int month,
		ShinyTableStyle style) implements ShinyComponent {

	// Package-private constructor, only accessible by the Builder
	public ShinyCalendar {
	}

	// Static factory method to get a new Builder instance
	public static ShinyCalendarBuilder builder() {
		return new ShinyCalendarBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinyCalendarRenderer().render(this, writer);
	}
}
