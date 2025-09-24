package io.vertigo.shiny.renderers.text;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.text.figlet.ShinyFigletFont;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyFigletStyle {
	private ShinyColor figletColor = ShinyColors.BLUE;
	private ShinyFigletFont figletFont = ShinyFigletFont.STANDARD; // Default figletFont

	public ShinyFigletStyle withColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.figletColor = color;
		return this;
	}

	public ShinyFigletStyle withFont(final ShinyFigletFont font) {
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
