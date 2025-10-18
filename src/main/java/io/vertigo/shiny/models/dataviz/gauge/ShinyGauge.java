package io.vertigo.shiny.models.dataviz.gauge;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyGauge(
		UUID id,
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
