package io.vertigo.shell.shiny.table;

import io.vertigo.core.lang.Assertion;

/**
 * Box-drawing characters for rendering table structures in console output.

 * To draw a box we need 11 chars 
 * 
 * 	┌────────┬────────┐  ==> TOP
 *  │        │        │
 *  ├────────┼────────┤  ==> INNER 
 *  │        │        │
 *  └────────┴────────┘  ==> BOTTOM
 * <Left> <Middle> <Right> 
 * 
 * Why 11 ? row(3) x Col(3) + horizontal + vertical 
 * 
 */
final class ShinyBorderChars {
	private static final String HORIZONTAL = "─"; // U+2500
	private static final String VERTICAL = "│"; // U+2502
	private static final String BLANK = " ";
	private static final String HEAVY_HORIZONTAL = "━"; // U+2501

	private static final String SQUARE_TOP_LEFT = "┌"; // U+250C
	private static final String SQUARE_TOP_MIDDLE = "┬"; // U+252C
	private static final String SQUARE_TOP_RIGHT = "┐"; // U+2510

	private static final String SQUARE_INNER_RIGHT = "┤"; // U+2524
	private static final String SQUARE_CENTER = "┼"; // U+253C //aka INNER_MIDDLE
	private static final String SQUARE_INNER_LEFT = "├"; // U+251C

	private static final String SQUARE_BOTTOM_LEFT = "└"; // U+2514
	private static final String SQUARE_BOTTOM_MIDDLE = "┴"; // U+2534
	private static final String SQUARE_BOTTOM_RIGHT = "┘"; // U+2518

	public static final String ROUNDED_TOP_LEFT = "╭"; // U+256D
	public static final String ROUNDED_TOP_MIDDLE = "┬"; // U+252C (pas de variante arrondie)
	public static final String ROUNDED_TOP_RIGHT = "╮"; // U+256E

	public static final String ROUNDED_INNER_RIGHT = "┤"; // U+2524 (pas de variante arrondie)
	public static final String ROUNDED_CENTER = "┼"; // U+253C (idem)
	public static final String ROUNDED_INNER_LEFT = "├"; // U+251C

	public static final String ROUNDED_BOTTOM_LEFT = "╰"; // U+2570
	public static final String ROUNDED_BOTTOM_MIDDLE = "┴"; // U+2534
	public static final String ROUNDED_BOTTOM_RIGHT = "╯"; // U+256F

	private ShinyBorder border;

	ShinyBorderChars(ShinyBorder border) {
		Assertion.check().isNotNull(border);
		//---
		this.border = border;
	}

	String topHorizontal() {
		return switch (border) {
			case Square, Rounded, Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "-";
		};
	}

	String innerHorizontal() {
		return switch (border) {
			case Square, Rounded, Horizontal, Simple -> HORIZONTAL;
			case SimpleHeavy -> HEAVY_HORIZONTAL;
			case Ascii -> "-";
		};
	}

	String bottomHorizontal() {
		return switch (border) {
			case Square, Rounded, Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "-";

		};
	}

	String vertical() {
		return switch (border) {
			case Square, Rounded -> VERTICAL;
			case Horizontal, Simple, SimpleHeavy -> BLANK;
			case Ascii -> "|";
		};
	}

	String topLeft() {
		return switch (border) {
			case Square -> SQUARE_TOP_LEFT;
			case Rounded -> ROUNDED_TOP_LEFT;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "+";
		};
	}

	String topMiddle() {
		return switch (border) {
			case Square -> SQUARE_TOP_MIDDLE;
			case Rounded -> ROUNDED_TOP_MIDDLE;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "-";
		};
	}

	String topRight() {
		return switch (border) {
			case Square -> SQUARE_TOP_RIGHT;
			case Rounded -> ROUNDED_TOP_RIGHT;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "+";
		};
	}

	String innerRight() {
		return switch (border) {
			case Square -> SQUARE_INNER_RIGHT;
			case Rounded -> ROUNDED_INNER_RIGHT;
			case Horizontal, Simple -> HORIZONTAL;
			case SimpleHeavy -> HEAVY_HORIZONTAL;
			case Ascii -> "|";
		};
	}

	String center() {
		return switch (border) {
			case Square -> SQUARE_CENTER;
			case Rounded -> ROUNDED_CENTER;
			case Horizontal, Simple -> HORIZONTAL;
			case SimpleHeavy -> HEAVY_HORIZONTAL;
			case Ascii -> "+";
		};
	}

	String innerLeft() {
		return switch (border) {
			case Square -> SQUARE_INNER_LEFT;
			case Rounded -> ROUNDED_INNER_LEFT;
			case Horizontal, Simple -> HORIZONTAL;
			case SimpleHeavy -> HEAVY_HORIZONTAL;
			case Ascii -> "|";
		};
	}

	String bottomLeft() {
		return switch (border) {
			case Square -> SQUARE_BOTTOM_LEFT;
			case Rounded -> ROUNDED_BOTTOM_LEFT;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "+";
		};
	}

	String bottomMiddle() {
		return switch (border) {
			case Square -> SQUARE_BOTTOM_MIDDLE;
			case Rounded -> ROUNDED_BOTTOM_MIDDLE;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "-";
		};
	}

	String bottomRight() {
		return switch (border) {
			case Square -> SQUARE_BOTTOM_RIGHT;
			case Rounded -> ROUNDED_BOTTOM_RIGHT;
			case Horizontal -> HORIZONTAL;
			case Simple, SimpleHeavy -> BLANK;
			case Ascii -> "+";
		};
	}
}
