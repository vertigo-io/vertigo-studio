package io.vertigo.shiny.components.dataviz.gauge;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyGaugeStyle {
	private ShinyColor gaugeColor = ShinyColors.GREEN;

	public ShinyGaugeStyle color(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.gaugeColor = color;
		return this;
	}

	ShinyColor color() {
		return gaugeColor;
	}
}
