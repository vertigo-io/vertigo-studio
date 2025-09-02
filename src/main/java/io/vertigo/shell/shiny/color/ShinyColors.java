package io.vertigo.shell.shiny.color;

public final class ShinyColors {
	public static final ShinyColor BLACK = new ShinyColor(30);
	public static final ShinyColor RED = new ShinyColor(31);
	public static final ShinyColor GREEN = new ShinyColor(32);
	public static final ShinyColor YELLOW = new ShinyColor(33);
	public static final ShinyColor BLUE = new ShinyColor(34);
	public static final ShinyColor MAGENTA = new ShinyColor(35);
	public static final ShinyColor CYAN = new ShinyColor(36);
	public static final ShinyColor WHITE = new ShinyColor(37);

	// Styles supplémentaires
	public static final String UNDERLINE = "\u001B[4m";
	public static final String BLINK = "\u001B[5m"; // support variable
	public static final String INVERSE = "\u001B[7m";
	public static final String DIM = "\u001B[2m";

	// Réinitialisation
	public static final String RESET = "\u001B[0m";

	private ShinyColors() {
	}
}
