package io.vertigo.shiny.renderers.data;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyRenderer;

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
final class ShinyTableBorderChars {
	private static final String UNICODE_HORIZONTAL = "─"; // U+2500
	private static final String UNICODE_VERTICAL = "│"; // U+2502
	private static final String UNICODE_BLANK = " ";
	private static final String UNICODE_HEAVY_HORIZONTAL = "━"; // U+2501

	private static final String UNICODE_SQUARE_TOP_LEFT = "┌"; // U+250C
	private static final String UNICODE_SQUARE_TOP_MIDDLE = "┬"; // U+252C
	private static final String UNICODE_SQUARE_TOP_RIGHT = "┐"; // U+2510

	private static final String UNICODE_SQUARE_INNER_RIGHT = "┤"; // U+2524
	private static final String UNICODE_SQUARE_CENTER = "┼"; // U+253C //aka INNER_MIDDLE
	private static final String UNICODE_SQUARE_INNER_LEFT = "├"; // U+251C

	private static final String UNICODE_SQUARE_BOTTOM_LEFT = "└"; // U+2514
	private static final String UNICODE_SQUARE_BOTTOM_MIDDLE = "┴"; // U+2534
	private static final String UNICODE_SQUARE_BOTTOM_RIGHT = "┘"; // U+2518

	public static final String UNICODE_ROUNDED_TOP_LEFT = "╭"; // U+256D
	public static final String UNICODE_ROUNDED_TOP_MIDDLE = "┬"; // U+252C (pas de variante arrondie)
	public static final String UNICODE_ROUNDED_TOP_RIGHT = "╮"; // U+256E

	public static final String UNICODE_ROUNDED_INNER_RIGHT = "┤"; // U+2524 (pas de variante arrondie)
	public static final String UNICODE_ROUNDED_CENTER = "┼"; // U+253C (idem)
	public static final String UNICODE_ROUNDED_INNER_LEFT = "├"; // U+251C

	public static final String UNICODE_ROUNDED_BOTTOM_LEFT = "╰"; // U+2570
	public static final String UNICODE_ROUNDED_BOTTOM_MIDDLE = "┴"; // U+2534
	public static final String UNICODE_ROUNDED_BOTTOM_RIGHT = "╯"; // U+256F

	private final ShinyTableBorder border;

	ShinyTableBorderChars(final ShinyTableBorder border) {
		Assertion.check().isNotNull(border);
		//---
		this.border = border;
	}

	private String horizontal() {
		return ShinyRenderer.theme().ascii() ? "-" : UNICODE_HORIZONTAL;
	}

	private String heavyHorizontal() {
		return ShinyRenderer.theme().ascii() ? "-" : UNICODE_HEAVY_HORIZONTAL;
	}

	private String verticalChar() {
		return ShinyRenderer.theme().ascii() ? "|" : UNICODE_VERTICAL;
	}

	private String blank() {
		return ShinyRenderer.theme().ascii() ? " " : UNICODE_BLANK;
	}

	String topHorizontal() {
		return switch (border) {
			case Normal, Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String innerHorizontal() {
		return switch (border) {
			case Normal, Horizontal, Simple -> horizontal();
			case SimpleHeavy -> heavyHorizontal();
		};
	}

	String bottomHorizontal() {
		return switch (border) {
			case Normal, Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String vertical() {
		return switch (border) {
			case Normal -> verticalChar();
			case Horizontal, Simple, SimpleHeavy -> blank();
		};
	}

	String topLeft() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square() ? UNICODE_SQUARE_TOP_LEFT : UNICODE_ROUNDED_TOP_LEFT;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String topMiddle() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_TOP_MIDDLE
							: UNICODE_ROUNDED_TOP_MIDDLE;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String topRight() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square() ? UNICODE_SQUARE_TOP_RIGHT : UNICODE_ROUNDED_TOP_RIGHT;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String innerRight() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_INNER_RIGHT
							: UNICODE_ROUNDED_INNER_RIGHT;
			case Horizontal, Simple -> horizontal();
			case SimpleHeavy -> heavyHorizontal();
		};
	}

	String center() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_CENTER
							: UNICODE_ROUNDED_CENTER;
			case Horizontal, Simple -> horizontal();
			case SimpleHeavy -> heavyHorizontal();
		};
	}

	String innerLeft() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_INNER_LEFT
							: UNICODE_ROUNDED_INNER_LEFT;
			case Horizontal, Simple -> horizontal();
			case SimpleHeavy -> heavyHorizontal();
		};
	}

	String bottomLeft() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_BOTTOM_LEFT
							: UNICODE_ROUNDED_BOTTOM_LEFT;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String bottomMiddle() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_BOTTOM_MIDDLE
							: UNICODE_ROUNDED_BOTTOM_MIDDLE;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}

	String bottomRight() {
		return switch (border) {
			case Normal -> ShinyRenderer.theme().ascii()
					? "+"
					: ShinyRenderer.theme().square()
							? UNICODE_SQUARE_BOTTOM_RIGHT
							: UNICODE_ROUNDED_BOTTOM_RIGHT;
			case Horizontal -> horizontal();
			case Simple, SimpleHeavy -> blank();
		};
	}
}
