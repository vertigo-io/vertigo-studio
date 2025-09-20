package io.vertigo.shiny.components.dataviz.gauge;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyGauge(
		String title,
		double value,
		double maxValue,
		@JsonIgnore ShinyGaugeStyle style) implements ShinyComponent {

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
