package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyRatingBuilder implements Builder<ShinyRating> {
	private String _label;
	private double _value = 0;
	private ShinyRatingScale _scale = ShinyRatingScale.SCALE_5;
	private int _customMaxValue = -1; // -1 means use scale
	private boolean _showValue = true;
	private boolean _showPercentage = false;
	private boolean _showBox = false;
	private String _separator = "";
	private boolean _allowHalfRating = false;

	public ShinyRatingBuilder withLabel(final String label) {
		this._label = label;
		return this;
	}

	public ShinyRatingBuilder withValue(final double value) {
		this._value = Math.max(0, value); // Ensure non-negative
		return this;
	}

	public ShinyRatingBuilder withScale(final ShinyRatingScale scale) {
		this._scale = scale;
		this._customMaxValue = -1; // Reset custom maxValue when using scale
		return this;
	}

	public ShinyRatingBuilder withMaxValue(final int maxValue) {
		this._customMaxValue = Math.max(1, maxValue); // Ensure positive
		return this;
	}

	public ShinyRatingBuilder withShowValue(final boolean show) {
		this._showValue = show;
		return this;
	}

	public ShinyRatingBuilder withShowPercentage(final boolean show) {
		this._showPercentage = show;
		return this;
	}

	public ShinyRatingBuilder withShowBox(final boolean show) {
		this._showBox = show;
		return this;
	}

	public ShinyRatingBuilder withSeparator(final String separator) {
		Assertion.check().isNotNull(separator);
		//---
		this._separator = separator;
		return this;
	}

	public ShinyRatingBuilder withAllowHalfRating(final boolean allow) {
		this._allowHalfRating = allow;
		return this;
	}

	@Override
	public ShinyRating build() {
		return new ShinyRating(
				_label,
				_value,
				_scale,
				_customMaxValue,
				_showValue,
				_showPercentage,
				_showBox,
				_separator,
				_allowHalfRating);
	}
}
