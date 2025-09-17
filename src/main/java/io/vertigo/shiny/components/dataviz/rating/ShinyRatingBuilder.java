package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyRatingBuilder implements Builder<ShinyRating> {
	private String ratingLabel;
	private double ratingValue = 0;
	private ShinyRatingScale ratingScale = ShinyRatingScale.SCALE_5;
	private int ratingCustomMaxValue = -1; // -1 means use scale
	private ShinyRatingStyle ratingStyle;
	private boolean showValue = true;
	private boolean showPercentage = false;
	private boolean showBox = false;
	private String ratingSeparator = "";
	private boolean allowHalfRating = false;

	// No public constructor, use ShinyRating.builder()
	ShinyRatingBuilder() {
		// Package-private constructor
		this.ratingStyle = Shiny.theme().ratingStyle(); // Initialize default style
	}

	public ShinyRatingBuilder withStyle(final ShinyRatingStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.ratingStyle = style;
		return this;
	}

	public ShinyRatingBuilder withLabel(final String label) {
		this.ratingLabel = label;
		return this;
	}

	public ShinyRatingBuilder withValue(final double value) {
		this.ratingValue = Math.max(0, value); // Ensure non-negative
		return this;
	}

	public ShinyRatingBuilder withScale(final ShinyRatingScale scale) {
		this.ratingScale = ratingScale;
		this.ratingCustomMaxValue = -1; // Reset custom maxValue when using scale
		return this;
	}

	public ShinyRatingBuilder withMaxValue(final int maxValue) {
		this.ratingCustomMaxValue = Math.max(1, maxValue); // Ensure positive
		return this;
	}

	public ShinyRatingBuilder withShowValue(final boolean show) {
		this.showValue = show;
		return this;
	}

	public ShinyRatingBuilder withShowPercentage(final boolean show) {
		this.showPercentage = show;
		return this;
	}

	public ShinyRatingBuilder withShowBox(final boolean show) {
		this.showBox = show;
		return this;
	}

	public ShinyRatingBuilder withSeparator(final String separator) {
		Assertion.check().isNotNull(separator);
		//---
		this.ratingSeparator = separator;
		return this;
	}

	public ShinyRatingBuilder withAllowHalfRating(final boolean allow) {
		this.allowHalfRating = allow;
		return this;
	}

	@Override
	public ShinyRating build() {
		return new ShinyRating(
				ratingLabel,
				ratingValue,
				ratingScale,
				ratingCustomMaxValue,
				ratingStyle,
				showValue,
				showPercentage,
				showBox,
				ratingSeparator,
				allowHalfRating);
	}
}
