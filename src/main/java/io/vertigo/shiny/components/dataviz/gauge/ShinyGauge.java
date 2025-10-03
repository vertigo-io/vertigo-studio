package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("gauge")
public record ShinyGauge(
		String title,
		double value,
		double maxValue) implements ShinyComponent {

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
