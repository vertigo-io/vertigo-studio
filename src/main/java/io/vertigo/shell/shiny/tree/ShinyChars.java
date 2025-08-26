package io.vertigo.shell.shiny.tree;

/**
 * Box-drawing characters for rendering tree structures in console output.
 */
final class ShinyChars {
	static final String HORIZONTAL = "─"; // U+2500
	static final String VERTICAL = "│"; // U+2502
	static final String INNER_LEFT = "├"; // U+251C
	static final String BOTTOM_LEFT = "└"; // U+2514

	private ShinyChars() {
	}

	//	String horizontal() {
	//		return HORIZONTAL;
	//	}
	//
	//
	//	String vertical() {
	//		return VERTICAL;
	//	}
	//
	//	String bottomLeft() {
	//		return BOTTOM_LEFT;
	//	}
	//
}
