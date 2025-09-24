package io.vertigo.shiny.renderers.dataviz;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyGaugeStyle {
	private ShinyColor gaugeColor = ShinyColors.GREEN;
	private int gaugeMaxLength = 50; // Longueur de la barre en caractères

	public ShinyGaugeStyle withColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.gaugeColor = color;
		return this;
	}

	ShinyColor color() {
		return gaugeColor;
	}

	public ShinyGaugeStyle withMaxLength(int maxLength) {
		Assertion.check()
				.isTrue(maxLength > 1, "maxlength mus be positive");
		//---
		this.gaugeMaxLength = maxLength;
		return this;
	}

	int maxLength() {
		return gaugeMaxLength;
	}
}
