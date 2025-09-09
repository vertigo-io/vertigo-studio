package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyGaugeStyle {
	public ShinyColor gaugeColor = ShinyColors.GREEN;

	public ShinyGaugeStyle color(final ShinyColor color) {
		this.gaugeColor = color;
		return this;
	}
}
