package io.vertigo.shiny.style;

public final class ShinyColor {
	private final int color;

	/**
	 * Creates a new ANSI color wrapper.
	 * @param color base ANSI color code (30–37 for foreground, etc.)
	 */
	ShinyColor(int color) {
		this.color = color;
	}

	/**
	 * Returns the ANSI escape sequence for the foreground color.
	 * Example: 31 → "\u001B[31m" (red).
	 */
	private String fg() {
		return String.format("\u001B[%sm", color);
	}

	/**
	 * Returns the ANSI escape sequence for the background color.
	 * Example: 31 → "\u001B[41m" (red background).
	 */
	private String bg() {
		return String.format("\u001B[%sm", color + 10);
	}

	public String fg(String s) {
		return fg() + s + ShinyReset.FG_DEFAULT;
	}

	public String bg(String s) {
		return bg() + s + ShinyReset.BG_DEFAULT;
	}

	@Override
	public String toString() {
		return fg();
	}
}
