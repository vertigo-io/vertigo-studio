package io.vertigo.shiny.models.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyGauge(
		String title,
		double value,
		double maxValue) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyGauge";
	}

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
