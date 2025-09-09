package io.vertigo.shiny.components.dataviz.barchart;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyBarChartStyle {
	public ShinyColor[] barColors = { ShinyColors.BLUE, ShinyColors.BLUE_BRIGHT };

	public ShinyBarChartStyle barColors(final ShinyColor... colors) {
		Assertion.check()
				.isNotNull(colors)
				.isTrue(colors.length > 1, "you need to put at least 2 colors");
		//---
		this.barColors = colors;
		return this;
	}
}
