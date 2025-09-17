package io.vertigo.shiny.components.text.toggle;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyToggleStyle {
	private ShinyColor onColor = ShinyColors.GREEN;
	private ShinyColor offColor = ShinyColors.RED;

	public ShinyToggleStyle withOnColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.onColor = color;
		return this;
	}

	public ShinyToggleStyle withOffColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.offColor = color;
		return this;
	}

	ShinyColor onColor() {
		return this.onColor;
	}

	ShinyColor offColor() {
		return this.offColor;
	}
}