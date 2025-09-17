package io.vertigo.shiny.components.data.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public final class ShinyCalendarBuilder implements Builder<ShinyCalendar> {
	final List<LocalDate> highlightedDates = new ArrayList<>();
	int year;
	int month;
	ShinyTableStyle calendarStyle;

	// No public constructor, use ShinyCalendar.builder()
	ShinyCalendarBuilder() {
		// Package-private constructor
		this.calendarStyle = Shiny.theme().calendarStyle(); // Initialize default style
		final LocalDate now = LocalDate.now();
		this.year = now.getYear();
		this.month = now.getMonthValue();
	}

	public ShinyCalendarBuilder withStyle(final ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.calendarStyle = style;
		return this;
	}

	public ShinyCalendarBuilder withYear(final int calendarYear) {
		this.year = calendarYear;
		return this;
	}

	public ShinyCalendarBuilder withMonth(final java.time.Month calendarMonth) {
		this.month = calendarMonth.getValue();
		return this;
	}

	public ShinyCalendarBuilder withMonth(final int calendarMonth) {
		this.month = calendarMonth;
		return this;
	}

	public ShinyCalendarBuilder addHighlightedDate(final LocalDate date) {
		highlightedDates.add(date);
		return this;
	}

	@Override
	public ShinyCalendar build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyCalendar(this);
	}
}
