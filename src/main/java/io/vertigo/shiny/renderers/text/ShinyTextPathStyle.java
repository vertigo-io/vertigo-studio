package io.vertigo.shiny.renderers.text;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyTextPathStyle {
	private ShinyColor rootColor = ShinyColors.GREEN;
	private ShinyColor nodeColor = ShinyColors.YELLOW;
	private ShinyColor leafColor = ShinyColors.BLUE_BRIGHT;
	private ShinyColor separatorColor = ShinyColors.RED;

	public ShinyTextPathStyle withRootColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.rootColor = color;
		return this;
	}

	public ShinyTextPathStyle withNodeColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.nodeColor = color;
		return this;
	}

	public ShinyTextPathStyle withLeafColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.leafColor = color;
		return this;
	}

	public ShinyTextPathStyle withSeparatorColor(final ShinyColor color) {
		Assertion.check().isNotNull(color);
		//---
		this.separatorColor = color;
		return this;
	}

	ShinyColor rootColor() {
		return this.rootColor;
	}

	ShinyColor nodeColor() {
		return this.nodeColor;
	}

	ShinyColor leafColor() {
		return this.leafColor;
	}

	ShinyColor separatorColor() {
		return this.separatorColor;
	}

}
