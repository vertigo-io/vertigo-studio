package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyGauge(
		String title,
		double value,
		double maxValue) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyGauge";
	}

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
