package io.vertigo.shiny.models.input.slider;

import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinySlider(
		@Nonnull UUID id,
		@Nonnull String label,
		double min,
		double max,
		double step,
		double value,
		String color,
		boolean thumbLabel) implements ShinyModel {

	public ShinySlider {
		Assertion.check().isNotNull(id);
		Assertion.check()
				.isNotBlank(label, "Label cannot be blank")
				.isTrue(max > min, "Max must be greater than min")
				.isTrue(step > 0, "Step must be positive")
				.isTrue(value >= min && value <= max, "Value must be between min and max");
	}
}
