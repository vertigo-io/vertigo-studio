package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyBarChartStyle {
	private ShinyColor[] barColors = { ShinyColors.BLUE, ShinyColors.BLUE_BRIGHT };
	private int barMaxLength = 50;

	public ShinyBarChartStyle withColors(final ShinyColor... colors) {
		Assertion.check()
				.isNotNull(colors)
				.isTrue(colors.length > 1, "you need to put at least 2 colors");
		//---
		this.barColors = colors;
		return this;
	}

	public ShinyBarChartStyle withMaxLength(int maxLength) {
		Assertion.check()
				.isTrue(maxLength > 1, "maxlength mus be positive");
		//---
		this.barMaxLength = maxLength;
		return this;
	}

	ShinyColor[] colors() {
		return barColors;
	}

	int maxLength() {
		return barMaxLength;
	}
}
