package io.vertigo.shell.shiny.table;

import io.vertigo.shell.shiny.utils.ShinyColors;

/**
 * Encapsulates all visual style parameters of the table.
 */
public final class ShinyTableStyle {
	private final ShinyTable table;

	boolean border = true;
	// Colors
	String titleBackgroundColor = ShinyColors.MAGENTA_BG;
	String titleTextColor = ShinyColors.WHITE;
	String headerBackgroundColor = ShinyColors.BLUE_BRIGHT_BG;
	String altRowBackgroundColor = ShinyColors.CYAN_BRIGHT_BG;
	String borderColor = ShinyColors.BLACK;

	ShinyTableStyle(ShinyTable table) {
		this.table = table;
	}

	public ShinyTableStyle border(boolean border) {
		this.border = border;
		return this;
	}

	public ShinyTableStyle titleBackgroundColor(String color) {
		this.titleBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle borderColor(String color) {
		this.borderColor = color;
		return this;
	}

	public ShinyTableStyle titleTextColor(String color) {
		this.titleTextColor = color;
		return this;
	}

	public ShinyTableStyle headerBackgroundColor(String color) {
		this.headerBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle altRowBackgroundColor(String color) {
		this.altRowBackgroundColor = color;
		return this;
	}

	public ShinyTable endStyle() {
		return table;
	}
}
