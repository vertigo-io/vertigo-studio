package io.vertigo.shell.shiny.table;

import io.vertigo.shell.shiny.utils.ShinyColors;

/**
 * Encapsulates all visual style parameters of the table.
 */
public final class ShinyTableStyle {
	private final ShinyTable table;

	ShinyBorder border = ShinyBorder.Square;
	// Colors
	String titleBackgroundColor = ShinyColors.MAGENTA_BG;
	String titleTextColor = ShinyColors.WHITE;
	String headerBackgroundColor = ShinyColors.BLUE_BRIGHT_BG;
	String altRowBackgroundColor = ShinyColors.CYAN_BRIGHT_BG;
	String borderColor = ShinyColors.BLACK;

	ShinyTableStyle(final ShinyTable table) {
		this.table = table;
	}

	public ShinyTableStyle border(final ShinyBorder tableBorder) {
		this.border = tableBorder;
		return this;
	}

	public ShinyTableStyle titleBackgroundColor(final String color) {
		this.titleBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle borderColor(final String color) {
		this.borderColor = color;
		return this;
	}

	public ShinyTableStyle titleTextColor(final String color) {
		this.titleTextColor = color;
		return this;
	}

	public ShinyTableStyle headerBackgroundColor(final String color) {
		this.headerBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle altRowBackgroundColor(final String color) {
		this.altRowBackgroundColor = color;
		return this;
	}

	public ShinyTable endStyle() {
		return table;
	}
}
