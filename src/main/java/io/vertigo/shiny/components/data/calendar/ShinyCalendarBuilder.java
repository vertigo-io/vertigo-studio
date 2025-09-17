package io.vertigo.shiny.components.data.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public final class ShinyCalendarBuilder implements Builder<ShinyCalendar> {
	private final List<LocalDate> highlightedDates = new ArrayList<>();
	private int calendarYear;
	private int calendarMonth;
	private ShinyTableStyle calendarStyle;

	// No public constructor, use ShinyCalendar.builder()
	ShinyCalendarBuilder() {
		// Package-private constructor
		this.calendarStyle = Shiny.theme().calendarStyle(); // Initialize default style
		final LocalDate now = LocalDate.now();
		this.calendarYear = now.getYear();
		this.calendarMonth = now.getMonthValue();
	}

	public ShinyCalendarBuilder withStyle(final ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.calendarStyle = style;
		return this;
	}

	public ShinyCalendarBuilder withYear(final int year) {
		this.calendarYear = year;
		return this;
	}

	public ShinyCalendarBuilder withMonth(final java.time.Month month) {
		this.calendarMonth = month.getValue();
		return this;
	}

	public ShinyCalendarBuilder withMonth(final int month) {
		this.calendarMonth = month;
		return this;
	}

	public ShinyCalendarBuilder addHighlightedDate(final LocalDate date) {
		highlightedDates.add(date);
		return this;
	}

	@Override
	public ShinyCalendar build() {
		return new ShinyCalendar(calendarYear, calendarMonth, calendarStyle);
	}
}
