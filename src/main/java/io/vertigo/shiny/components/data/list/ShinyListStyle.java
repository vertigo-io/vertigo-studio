package io.vertigo.shiny.components.data.list;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyListStyle {
	private ShinyColor itemColor = ShinyColors.BLUE_BRIGHT;
	private ShinyColor bulletColor = ShinyColors.CYAN;

	public ShinyListStyle itemColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.itemColor = color;
		return this;
	}

	public ShinyListStyle bulletColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.bulletColor = color;
		return this;
	}

	ShinyColor itemColor() {
		return this.itemColor;
	}

	ShinyColor bulletColor() {
		return this.bulletColor;
	}
}
