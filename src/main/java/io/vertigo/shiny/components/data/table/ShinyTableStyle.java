package io.vertigo.shiny.components.data.table;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

/**
 * Encapsulates all visual style parameters of the table.
 */
public final class ShinyTableStyle {

	ShinyBorder tableBorder = ShinyBorder.Normal;
	// Colors
	ShinyColor titleBackgroundColor = ShinyColors.MAGENTA;
	ShinyColor titleTextColor = ShinyColors.WHITE;
	ShinyColor headerBackgroundColor = ShinyColors.BLUE_BRIGHT;
	ShinyColor altRowBackgroundColor = ShinyColors.CYAN_BRIGHT;
	ShinyColor borderColor = ShinyColors.BLACK;

	public ShinyTableStyle border(final ShinyBorder border) {
		Assertion.check().isNotNull(border);
		//---
		this.tableBorder = border;
		return this;
	}

	public ShinyTableStyle titleBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.titleBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle borderColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.borderColor = color;
		return this;
	}

	public ShinyTableStyle titleTextColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.titleTextColor = color;
		return this;
	}

	public ShinyTableStyle headerBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.headerBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle altRowBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.altRowBackgroundColor = color;
		return this;
	}
}
