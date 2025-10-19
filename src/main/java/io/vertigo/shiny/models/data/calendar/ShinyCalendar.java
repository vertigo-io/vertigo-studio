package io.vertigo.shiny.models.data.calendar;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyCalendar(
		UUID id,
		int year,
		int month) implements ShinyModel {

	public ShinyCalendar {
		Assertion.check().isNotNull(id);
	}
}
