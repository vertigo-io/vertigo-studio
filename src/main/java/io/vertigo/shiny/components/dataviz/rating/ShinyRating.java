package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyRating(
		String label,
		double value,
		ShinyRatingScale scale,
		int customMaxValue, // -1 means use scale
		ShinyRatingStyle style,
		boolean showValue,
		boolean showPercentage,
		boolean showBox,
		String separator,
		boolean allowHalfRating) implements ShinyComponent {

	// Package-private constructor, only accessible by the Builder
	public ShinyRating {
	}

	// Static factory method to get a new Builder instance
	public static ShinyRatingBuilder builder() {
		return new ShinyRatingBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinyRatingRenderer().render(this, writer);
	}
}
