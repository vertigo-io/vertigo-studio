package io.vertigo.shiny.components.data.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.components.data.table.ShinyTableStyle;

public final class ShinyCalendar implements ShinyComponent {
	private final List<LocalDate> highlightedDates = new ArrayList<>();
	private int year;
	private int month;
	private ShinyTableStyle calendarStyle;

	public ShinyCalendar() {
		this.calendarStyle = Shiny.theme().calendarStyle();
		final LocalDate now = LocalDate.now();
		this.year = now.getYear();
		this.month = now.getMonthValue();
	}

	public ShinyCalendar style(final ShinyTableStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.calendarStyle = style;
		return this;
	}

	public ShinyCalendar year(final int calendarYear) {
		this.year = calendarYear;
		return this;
	}

	public ShinyCalendar month(final java.time.Month calendarMonth) {
		this.month = calendarMonth.getValue();
		return this;
	}

	public ShinyCalendar month(final int calendarMonth) {
		this.month = calendarMonth;
		return this;
	}

	public ShinyCalendar highlight(final LocalDate date) {
		highlightedDates.add(date);
		return this;
	}

	public void render(ShinyWriter writer) {
		final Locale calendarLocale = Shiny.theme().locale();
		final LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
		final LocalDate lastDayOfMonth = firstDayOfMonth.plusMonths(1).minusDays(1);

		final String monthName = firstDayOfMonth.getMonth().getDisplayName(TextStyle.FULL, calendarLocale);

		final String[] days = new String[7];
		for (int i = 0; i < 7; i++) {
			final DayOfWeek dayOfWeek = DayOfWeek.of(((i + WeekFields.of(calendarLocale).getFirstDayOfWeek().getValue() - 1) % 7) + 1);
			days[i] = String.format("%s ", dayOfWeek.getDisplayName(TextStyle.SHORT, calendarLocale));
		}

		final List<String[]> rows = new ArrayList<>();
		// Print days
		int dayOfWeekValue = firstDayOfMonth.getDayOfWeek().getValue();
		// Adjust for locale's first day of week
		int startOffset = (dayOfWeekValue - WeekFields.of(calendarLocale).getFirstDayOfWeek().getValue() + 7) % 7;
		String[] row = new String[7];
		for (int day = 1; day <= lastDayOfMonth.getDayOfMonth(); day++) {
			//final LocalDate currentDate = LocalDate.of(year, month, day);
			final String dayString = String.format("%2d", day);
			//			String coloredDayString = (highlightedDates.contains(currentDate)
			//					? ShinyColors.YELLOW_BRIGHT
			//					: ShinyColors.GREEN_BRIGHT)
			//					+ dayString + ShinyColors.RESET;
			int currentPosition = (day + startOffset + 6) % 7;
			row[currentPosition] = dayString; //String.format("%s ", coloredDayString);

			if (currentPosition == 6 || day == lastDayOfMonth.getDayOfMonth()) {
				rows.add(row);
				row = new String[7];
			}
		}
		Shiny.table()
				.title(String.format("%s %d", monthName, year))
				.header(days)
				.style(calendarStyle)
				.rows(rows)
				.render(writer);
	}
}
