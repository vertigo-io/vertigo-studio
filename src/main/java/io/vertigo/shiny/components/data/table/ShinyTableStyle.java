package io.vertigo.shiny.components.data.table;

import io.vertigo.shiny.color.ShinyColor;
import io.vertigo.shiny.color.ShinyColors;

/**
 * Encapsulates all visual style parameters of the table.
 */
public final class ShinyTableStyle {
	private final ShinyTable table;

	ShinyBorder border = ShinyBorder.Normal;
	// Colors
	ShinyColor titleBackgroundColor = ShinyColors.MAGENTA;
	ShinyColor titleTextColor = ShinyColors.WHITE;
	ShinyColor headerBackgroundColor = ShinyColors.BLUE.bright();
	ShinyColor altRowBackgroundColor = ShinyColors.CYAN.bright();
	ShinyColor borderColor = ShinyColors.BLACK;

	ShinyTableStyle(final ShinyTable table) {
		this.table = table;
	}

	public ShinyTableStyle border(final ShinyBorder tableBorder) {
		this.border = tableBorder;
		return this;
	}

	public ShinyTableStyle titleBackgroundColor(final ShinyColor color) {
		this.titleBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle borderColor(final ShinyColor color) {
		this.borderColor = color;
		return this;
	}

	public ShinyTableStyle titleTextColor(final ShinyColor color) {
		this.titleTextColor = color;
		return this;
	}

	public ShinyTableStyle headerBackgroundColor(final ShinyColor color) {
		this.headerBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle altRowBackgroundColor(final ShinyColor color) {
		this.altRowBackgroundColor = color;
		return this;
	}

	public ShinyTable endStyle() {
		return table;
	}
}
