package io.vertigo.shiny.models.data.calendar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Builder;

public final class ShinyCalendarBuilder implements Builder<ShinyCalendar> {
	private final List<LocalDate> _highlightedDates = new ArrayList<>();
	private int _year;
	private int _month;

	public ShinyCalendarBuilder() {
		final LocalDate now = LocalDate.now();
		this._year = now.getYear();
		this._month = now.getMonthValue();
	}

	public ShinyCalendarBuilder withYear(final int year) {
		this._year = year;
		return this;
	}

	public ShinyCalendarBuilder withMonth(final java.time.Month month) {
		this._month = month.getValue();
		return this;
	}

	public ShinyCalendarBuilder withMonth(final int month) {
		this._month = month;
		return this;
	}

	public ShinyCalendarBuilder addHighlightedDate(final LocalDate date) {
		_highlightedDates.add(date);
		return this;
	}

	@Override
	public ShinyCalendar build() {
		return new ShinyCalendar(_year, _month);
	}
}
