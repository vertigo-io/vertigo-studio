package io.vertigo.shiny.components.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.renderers.ShinyStyle;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyJsonStyle implements ShinyStyle<ShinyJson> {
	private ShinyColor labelColor = ShinyColors.BLUE;

	private ShinyColor numberColor = ShinyColors.GREEN;
	private ShinyColor stringColor = ShinyColors.RED;
	private ShinyColor booleanColor = ShinyColors.BLACK_BRIGHT;
	private ShinyColor nullColor = ShinyColors.BLACK_BRIGHT;

	private ShinyColor colonColor = ShinyColors.YELLOW;

	private ShinyColor commaColor = ShinyColors.WHITE;
	private ShinyColor bracketColor = ShinyColors.WHITE;
	private ShinyColor bracesColor = ShinyColors.WHITE;

	public ShinyJsonStyle withLabelColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.labelColor = color;
		return this;
	}

	public ShinyJsonStyle withNumberColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.numberColor = color;
		return this;
	}

	public ShinyJsonStyle withStringColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.stringColor = color;
		return this;
	}

	public ShinyJsonStyle withBooleanColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.booleanColor = color;
		return this;
	}

	public ShinyJsonStyle withNullColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.nullColor = color;
		return this;
	}

	public ShinyJsonStyle withColonColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.colonColor = color;
		return this;
	}

	public ShinyJsonStyle withCommaColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.commaColor = color;
		return this;
	}

	public ShinyJsonStyle withBracketColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.bracketColor = color;
		return this;
	}

	public ShinyJsonStyle withBracesColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.bracesColor = color;
		return this;
	}

	ShinyColor labelColor() {
		return this.labelColor;
	}

	ShinyColor numberColor() {
		return this.numberColor;
	}

	ShinyColor stringColor() {
		return this.stringColor;
	}

	ShinyColor booleanColor() {
		return this.booleanColor;
	}

	ShinyColor nullColor() {
		return this.nullColor;
	}

	ShinyColor colonColor() {
		return this.colonColor;
	}

	ShinyColor commaColor() {
		return this.commaColor;
	}

	ShinyColor bracketColor() {
		return this.bracketColor;
	}

	ShinyColor bracesColor() {
		return this.bracesColor;
	}
}
