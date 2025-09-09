package io.vertigo.shiny.components.data.json;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJsonStyle {
	public ShinyColor labelColor = ShinyColors.BLUE;

	public ShinyColor numberColor = ShinyColors.GREEN;
	public ShinyColor stringColor = ShinyColors.RED;
	public ShinyColor booleanColor = ShinyColors.BLACK_BRIGHT;
	public ShinyColor nullColor = ShinyColors.BLACK_BRIGHT;

	public ShinyColor colonColor = ShinyColors.YELLOW;

	public ShinyColor commaColor = ShinyColors.WHITE;
	public ShinyColor bracketColor = ShinyColors.WHITE;
	public ShinyColor bracesColor = ShinyColors.WHITE;

	public ShinyJsonStyle labelColor(final ShinyColor color) {
		this.labelColor = color;
		return this;
	}

	public ShinyJsonStyle numberColor(final ShinyColor color) {
		this.numberColor = color;
		return this;
	}

	public ShinyJsonStyle stringColor(final ShinyColor color) {
		this.stringColor = color;
		return this;
	}

	public ShinyJsonStyle booleanColor(final ShinyColor color) {
		this.booleanColor = color;
		return this;
	}

	public ShinyJsonStyle nullColor(final ShinyColor color) {
		this.nullColor = color;
		return this;
	}

	public ShinyJsonStyle colonColor(final ShinyColor color) {
		this.colonColor = color;
		return this;
	}

	public ShinyJsonStyle commaColor(final ShinyColor color) {
		this.commaColor = color;
		return this;
	}

	public ShinyJsonStyle bracketColor(final ShinyColor color) {
		this.bracketColor = color;
		return this;
	}

	public ShinyJsonStyle bracesColor(final ShinyColor color) {
		this.bracesColor = color;
		return this;
	}
}
