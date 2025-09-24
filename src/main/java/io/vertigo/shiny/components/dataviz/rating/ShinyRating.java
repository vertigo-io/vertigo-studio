package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyRating(
		String label,
		double value,
		ShinyRatingScale scale,
		int customMaxValue, // -1 means use scale
		boolean showValue,
		boolean showPercentage,
		boolean showBox,
		String separator,
		boolean allowHalfRating) implements ShinyComponent {
}
