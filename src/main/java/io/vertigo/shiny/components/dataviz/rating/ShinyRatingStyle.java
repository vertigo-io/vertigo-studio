package io.vertigo.shiny.components.dataviz.rating;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyRatingStyle {
	private ShinyColor filledColor = ShinyColors.YELLOW;
	private ShinyColor emptyColor = ShinyColors.WHITE;
	private ShinyRatingType ratingType = ShinyRatingType.STAR;

	public ShinyRatingStyle withType(final ShinyRatingType type) {
		Assertion.check().isNotNull(type);
		//---
		this.ratingType = type;
		return this;
	}

	public ShinyRatingStyle withFilledColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.filledColor = color;
		return this;
	}

	public ShinyRatingStyle withEmptyColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.emptyColor = color;
		return this;
	}

	ShinyRatingType type() {
		return this.ratingType;
	}

	ShinyColor filledColor() {
		return this.filledColor;
	}

	ShinyColor emptyColor() {
		return this.emptyColor;
	}

}