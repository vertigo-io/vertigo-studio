package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyFigletStyle {
	private ShinyColor figletColor = ShinyColors.BLUE;
	private ShinyFigletFont figletFont = ShinyFigletFont.STANDARD; // Default figletFont

	public ShinyFigletStyle color(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.figletColor = color;
		return this;
	}

	public ShinyFigletStyle font(final ShinyFigletFont font) {
		Assertion.check().isNotNull(font);
		//---
		this.figletFont = font;
		return this;
	}

	ShinyColor color() {
		return this.figletColor;
	}

	ShinyFigletFont font() {
		return this.figletFont;
	}
}
