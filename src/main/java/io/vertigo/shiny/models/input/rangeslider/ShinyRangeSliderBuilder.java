package io.vertigo.shiny.models.input.rangeslider;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public final class ShinyRangeSliderBuilder {
	private String _label;
	private int _min = 0;
	private int _max = 100;
	private int _step = 1;
	private List<Integer> _value = List.of(0, 100);
	private String _color;
	private boolean _thumbLabel;

	public ShinyRangeSliderBuilder withLabel(@Nonnull final String label) {
		Assertion.check().isNotBlank(label);
		//---
		_label = label;
		return this;
	}

	public ShinyRangeSliderBuilder withMin(final int min) {
		_min = min;
		return this;
	}

	public ShinyRangeSliderBuilder withMax(final int max) {
		_max = max;
		return this;
	}

	public ShinyRangeSliderBuilder withStep(final int step) {
		Assertion.check().isTrue(step > 0, "Step must be greater than 0");
		//---
		_step = step;
		return this;
	}

	public ShinyRangeSliderBuilder withValue(final int value1, final int value2) {
		_value = List.of(value1, value2);
		return this;
	}

	public ShinyRangeSliderBuilder withColor(final String color) {
		_color = color;
		return this;
	}

	public ShinyRangeSliderBuilder withThumbLabel(final boolean thumbLabel) {
		_thumbLabel = thumbLabel;
		return this;
	}

	public ShinyRangeSlider build() {
		return new ShinyRangeSlider(_label, _min, _max, _step, _value, _color, _thumbLabel);
	}
}
