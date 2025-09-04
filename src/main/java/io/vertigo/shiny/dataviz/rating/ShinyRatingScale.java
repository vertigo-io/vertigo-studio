package io.vertigo.shiny.dataviz.rating;

public enum ShinyRatingScale {
	SCALE_5(5),
	SCALE_10(10),
	SCALE_100(100);

	private final int maxValue;

	ShinyRatingScale(final int maxValue) {
		this.maxValue = maxValue;
	}

	public int getMaxValue() {
		return maxValue;
	}
}
