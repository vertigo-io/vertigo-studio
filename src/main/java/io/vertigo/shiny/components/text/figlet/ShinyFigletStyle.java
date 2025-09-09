package io.vertigo.shiny.components.text.figlet;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyFigletStyle {
	public ShinyColor figletColor = ShinyColors.BLUE;
	public ShinyFigletFonts figletFont = ShinyFigletFonts.STANDARD; // Default figletFont

	public ShinyFigletStyle color(final ShinyColor color) {
		this.figletColor = color;
		return this;
	}

	public ShinyFigletStyle font(final ShinyFigletFonts font) {
		this.figletFont = font;
		return this;
	}
}
