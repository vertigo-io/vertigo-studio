package io.vertigo.shiny.models.dataviz.rating;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyRating(
		String label,
		double value,
		ShinyRatingScale scale,
		int customMaxValue, // -1 means use scale
		boolean showValue,
		boolean showPercentage,
		boolean showBox,
		String separator,
		boolean allowHalfRating) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyRating";
	}

}
