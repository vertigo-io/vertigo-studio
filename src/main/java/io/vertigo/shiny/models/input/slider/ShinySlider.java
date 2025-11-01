package io.vertigo.shiny.models.input.slider;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyElement;

public record ShinySlider(
		UUID id,
		String label,
		double min,
		double max,
		double step,
		double value,
		String color,
		boolean thumbLabel) implements ShinyElement {

	public ShinySlider {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(label, "Label cannot be blank")
				.isTrue(max > min, "Max must be greater than min")
				.isTrue(step > 0, "Step must be positive")
				.isTrue(value >= min && value <= max, "Value must be between min and max");
	}
}
