package io.vertigo.shiny.style;

public final class ShinyColors {
	public static final ShinyColor BLACK = new ShinyColor(30);
	public static final ShinyColor RED = new ShinyColor(31);
	public static final ShinyColor GREEN = new ShinyColor(32);
	public static final ShinyColor YELLOW = new ShinyColor(33);
	public static final ShinyColor BLUE = new ShinyColor(34);
	public static final ShinyColor MAGENTA = new ShinyColor(35);
	public static final ShinyColor CYAN = new ShinyColor(36);
	public static final ShinyColor WHITE = new ShinyColor(37);

	// --- Bright variants (90–97)
	public static final ShinyColor BLACK_BRIGHT = new ShinyColor(90);
	public static final ShinyColor RED_BRIGHT = new ShinyColor(91);
	public static final ShinyColor GREEN_BRIGHT = new ShinyColor(92);
	public static final ShinyColor YELLOW_BRIGHT = new ShinyColor(93);
	public static final ShinyColor BLUE_BRIGHT = new ShinyColor(94);
	public static final ShinyColor MAGENTA_BRIGHT = new ShinyColor(95);
	public static final ShinyColor CYAN_BRIGHT = new ShinyColor(96);
	public static final ShinyColor WHITE_BRIGHT = new ShinyColor(97);
	// Réinitialisation
	public static final String RESET = "\u001B[0m";

	private ShinyColors() {
	}
}
