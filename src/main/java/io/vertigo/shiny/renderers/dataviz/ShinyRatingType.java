package io.vertigo.shiny.renderers.dataviz;

public enum ShinyRatingType {
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

	ShinyRatingType(final String filledIcon, final String emptyIcon) {
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
