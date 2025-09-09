package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyToggleStyle {
	public ShinyColor onColor = ShinyColors.GREEN;
	public ShinyColor offColor = ShinyColors.RED;

	public ShinyToggleStyle onColor(final ShinyColor color) {
		this.onColor = color;
		return this;
	}

	public ShinyToggleStyle offColor(final ShinyColor color) {
		this.offColor = color;
		return this;
	}
}
