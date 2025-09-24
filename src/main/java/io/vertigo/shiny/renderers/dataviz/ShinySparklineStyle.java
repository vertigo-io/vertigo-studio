package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinySparklineStyle {
	private ShinyColor sparklineColor = ShinyColors.BLUE;

	public ShinySparklineStyle withColor(final ShinyColor color) {
		this.sparklineColor = color;
		return this;
	}

	ShinyColor color() {
		return this.sparklineColor;
	}
}
