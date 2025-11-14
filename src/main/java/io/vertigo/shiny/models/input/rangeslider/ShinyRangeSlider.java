package io.vertigo.shiny.models.input.rangeslider;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyRangeSlider(String label, int min, int max, int step, List<Integer> value, String color, boolean thumbLabel) implements ShinyBlock {
	public ShinyRangeSlider {
		Assertion.check()
				.isNotBlank(label)
				.isTrue(min < max, "Min must be less than max")
				.isTrue(step > 0, "Step must be greater than 0")
				.isNotNull(value)
				.isTrue(value.size() == 2, "Value must contain two integers for min and max range");
	}
}
