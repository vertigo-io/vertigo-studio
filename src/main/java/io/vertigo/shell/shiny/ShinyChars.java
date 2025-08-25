package io.vertigo.shell.shiny;

/**
 * Box-drawing characters for rendering simple structures
 * such as tables, trees, and grids in console output.
 */
public final class ShinyChars {

	// Single lines
	public static final String HORIZONTAL = "─"; // U+2500
	public static final String HEAVY_HORIZONTAL = "━"; // U+2501
	public static final String VERTICAL = "│"; // U+2502

	// Single corners
	public static final String TOP_LEFT = "┌"; // U+250C
	public static final String TOP_RIGHT = "┐"; // U+2510
	public static final String BOTTOM_LEFT = "└"; // U+2514
	public static final String BOTTOM_RIGHT = "┘"; // U+2518

	// Single intersections
	public static final String T_LEFT = "┤"; // U+2524
	public static final String T_RIGHT = "├"; // U+251C
	public static final String T_TOP = "┴"; // U+2534
	public static final String T_BOTTOM = "┬"; // U+252C
	public static final String CROSS = "┼"; // U+253C

	private ShinyChars() {
		// Prevent instantiation
	}
}
