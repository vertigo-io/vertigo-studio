package io.vertigo.shiny.components.data.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinyCalendarBuilder implements Builder<ShinyCalendar> {
	private final List<LocalDate> highlightedDates = new ArrayList<>();
	private int calendarYear;
	private int calendarMonth;

	public ShinyCalendarBuilder() {
		final LocalDate now = LocalDate.now();
		this.calendarYear = now.getYear();
		this.calendarMonth = now.getMonthValue();
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
		return new ShinyCalendar(calendarYear, calendarMonth);
	}
}
