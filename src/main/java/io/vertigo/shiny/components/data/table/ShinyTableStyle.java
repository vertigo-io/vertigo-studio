package io.vertigo.shiny.components.data.table;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

/**
 * Encapsulates all visual style parameters of the table.
 */
public final class ShinyTableStyle {
	private ShinyBorder tableBorder = ShinyBorder.Normal;
	// Colors
	private ShinyColor titleBackgroundColor = ShinyColors.MAGENTA;
	private ShinyColor titleTextColor = ShinyColors.WHITE;
	private ShinyColor headerBackgroundColor = ShinyColors.BLUE_BRIGHT;
	private ShinyColor altRowBackgroundColor = ShinyColors.CYAN_BRIGHT;
	private ShinyColor borderColor = ShinyColors.BLACK;

	public ShinyTableStyle withBorder(final ShinyBorder border) {
		Assertion.check().isNotNull(border);
		//---
		this.tableBorder = border;
		return this;
	}

	public ShinyTableStyle withTitleBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.titleBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle withBorderColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.borderColor = color;
		return this;
	}

	public ShinyTableStyle withTitleTextColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.titleTextColor = color;
		return this;
	}

	public ShinyTableStyle withHeaderBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.headerBackgroundColor = color;
		return this;
	}

	public ShinyTableStyle withAltRowBackgroundColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.altRowBackgroundColor = color;
		return this;
	}

	ShinyBorder border() {
		return this.tableBorder;
	}

	ShinyColor titleBackgroundColor() {
		return this.titleBackgroundColor;
	}

	ShinyColor borderColor() {
		return this.borderColor;
	}

	ShinyColor titleTextColor() {
		return this.titleTextColor;
	}

	ShinyColor headerBackgroundColor() {
		return this.headerBackgroundColor;
	}

	ShinyColor altRowBackgroundColor() {
		return this.altRowBackgroundColor;
	}

}