package io.vertigo.shiny.components.dataviz.gauge;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyGauge(
		String title,
		double value,
		double maxValue,
		@JsonIgnore ShinyGaugeStyle style) implements ShinyComponent {

	public ShinyGauge {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyGaugeBuilder builder() {
		return new ShinyGaugeBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyGaugeRenderer.render(this, writer);
	}
}
