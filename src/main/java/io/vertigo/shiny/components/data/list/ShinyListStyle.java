package io.vertigo.shiny.components.data.list;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyListStyle {
	public ShinyColor itemColor = ShinyColors.BLUE_BRIGHT;
	public ShinyColor bulletColor = ShinyColors.CYAN;

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
}
