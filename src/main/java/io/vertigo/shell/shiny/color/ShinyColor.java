package io.vertigo.shell.shiny.color;

public final class ShinyColor {
	private final int color;

	ShinyColor(int color) {
		this.color = color;
	}

	public ShinyColor bright() {
		return new ShinyColor(color + 60);
	}

	public String fg() {
		return String.format("\u001B[%sm", color);
	}

	public String toString() {
		return fg();
	}

	public String bg() {
		return String.format("\u001B[%sm", color + 10);
	}

	// --- Couleurs de police standard
	// BLACK = "\u001B[30m";

	// --- Couleurs d'arrière-plan standard
	// BLACK_BG = "\u001B[40m";

	// --- Couleurs de police plus claires (bright)
	// BLACK_BRIGHT = "\u001B[90m";

	// --- Couleurs d'arrière-plan plus claires (bright)
	// BLACK_BRIGHT_BG = "\u001B[100m";

}
