package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyRatingBuilder implements Builder<ShinyRating> {
	String label;
	double value = 0;
	ShinyRatingScale scale = ShinyRatingScale.SCALE_5;
	int customMaxValue = -1; // -1 means use scale
	ShinyRatingStyle ratingStyle;
	boolean showValue = true;
	boolean showPercentage = false;
	boolean showBox = false;
	String separator = "";
	boolean allowHalfRating = false;

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

	public ShinyRatingBuilder withLabel(final String text) {
		this.label = text;
		return this;
	}

	public ShinyRatingBuilder withValue(final double currentValue) {
		this.value = Math.max(0, currentValue); // Ensure non-negative
		return this;
	}

	public ShinyRatingBuilder withScale(final ShinyRatingScale ratingScale) {
		this.scale = ratingScale;
		this.customMaxValue = -1; // Reset custom max when using scale
		return this;
	}

	public ShinyRatingBuilder withMaxValue(final int max) {
		this.customMaxValue = Math.max(1, max); // Ensure positive
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

	public ShinyRatingBuilder withSeparator(final String sep) {
		Assertion.check().isNotNull(sep);
		//---
		this.separator = sep;
		return this;
	}

	public ShinyRatingBuilder withAllowHalfRating(final boolean allow) {
		this.allowHalfRating = allow;
		return this;
	}

	@Override
	public ShinyRating build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyRating(this);
	}
}
