package io.vertigo.shiny.models.dataviz.calendar;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyCalendar(
		UUID id,
		int year,
		int month) implements ShinyBlock {

	public ShinyCalendar {
		Assertion.check().isNotNull(id);
	}
}
