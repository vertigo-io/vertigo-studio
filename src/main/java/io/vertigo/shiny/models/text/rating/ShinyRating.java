package io.vertigo.shiny.models.text.rating;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyElement;

public record ShinyRating(
		UUID id,
		String label,
		double value,
		ShinyRatingScale scale,
		int customMaxValue, // -1 means use scale
		boolean showValue,
		boolean showPercentage,
		boolean showBox,
		String separator,
		boolean allowHalfRating) implements ShinyElement {
}
