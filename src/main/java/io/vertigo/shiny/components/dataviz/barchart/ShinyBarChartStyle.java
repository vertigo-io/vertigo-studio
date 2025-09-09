package io.vertigo.shiny.components.dataviz.barchart;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyBarChartStyle {
	public ShinyColor[] barColors = { ShinyColors.BLUE, ShinyColors.BLUE_BRIGHT };

	public ShinyBarChartStyle barColors(final ShinyColor... colors) {
		this.barColors = colors;
		return this;
	}
}
