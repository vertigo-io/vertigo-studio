package io.vertigo.shiny.dataviz.rating;

public enum ShinyRatingStyle {
	STAR("★", "☆"),
	CIRCLE("●", "○"),
	SQUARE("■", "□"),
	DIAMOND("♦", "♢"),
	HEART("♥", "♡"),
	ARROW("▶", "▷"),
	DOT("●", "·"),
	PLUS("+", "-"),
	THUMB("👍", "👎"),
	FIRE("🔥", "❄"),
	SMILE("😊", "😐"),
	BATTERY("🔋", "🪫");

	private final String filledIcon;
	private final String emptyIcon;

	ShinyRatingStyle(final String filledIcon, final String emptyIcon) {
		this.filledIcon = filledIcon;
		this.emptyIcon = emptyIcon;
	}

	String getFilledIcon() {
		return filledIcon;
	}

	String getEmptyIcon() {
		return emptyIcon;
	}
}
