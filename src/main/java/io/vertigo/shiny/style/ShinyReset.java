package io.vertigo.shiny.style;

/**
 * ANSI Reset codes to disable specific styles or colors.
 */
public final class ShinyReset {

	private ShinyReset() {
		// utility class
	}

	// --- Global reset
	public static final String ALL = "\u001B[0m"; // reset all attributes

	// --- Intensity
	public static final String BOLD_OFF = "\u001B[21m"; // disable bold (sometimes double underline)
	public static final String NORMAL_INTENSITY = "\u001B[22m"; // disable bold + dim

	// --- Italic
	public static final String ITALIC_OFF = "\u001B[23m";

	// --- Underline
	public static final String UNDERLINE_OFF = "\u001B[24m";

	// --- Blink
	public static final String BLINK_OFF = "\u001B[25m";

	// --- Inverse
	public static final String INVERSE_OFF = "\u001B[27m";

	// --- Hidden
	public static final String REVEAL = "\u001B[28m"; // disable hidden

	// --- Strikethrough
	public static final String STRIKETHROUGH_OFF = "\u001B[29m";

	// --- Colors
	public static final String FG_DEFAULT = "\u001B[39m"; // default foreground color
	public static final String BG_DEFAULT = "\u001B[49m"; // default background color
	//	public static final String UL_DEFAULT = "\u001B[59m"; // default underline color (rare support)
}
