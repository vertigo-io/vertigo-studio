package io.vertigo.shiny.renderers.data;

/**
 * Box-drawing characters for rendering tree structures in console output.
 */
final class ShinyTreeChars {
	static final String HORIZONTAL = "─"; // U+2500
	static final String VERTICAL = "│"; // U+2502
	static final String INNER_LEFT = "├"; // U+251C
	static final String BOTTOM_LEFT = "└"; // U+2514

	//	public static final String FOLDER = "📁"; // U+1F4C1
	//	public static final String OPEN_FOLDER = "📂"; // U+1F4C2

	private ShinyTreeChars() {
	}

}
