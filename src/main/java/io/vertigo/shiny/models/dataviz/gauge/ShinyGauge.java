package io.vertigo.shiny.models.dataviz.gauge;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyGauge(
		UUID id,
		String title,
		double value,
		double maxValue) implements ShinyBlock {

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
