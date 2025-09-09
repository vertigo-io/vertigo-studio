package io.vertigo.shiny.components.text.textpath;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public final class ShinyTextPathStyle {
	public ShinyColor rootColor = ShinyColors.GREEN;
	public ShinyColor nodeColor = ShinyColors.YELLOW;
	public ShinyColor leafColor = ShinyColors.BLUE_BRIGHT;
	public ShinyColor separatorColor = ShinyColors.RED;

	public ShinyTextPathStyle rootColor(final ShinyColor color) {
		this.rootColor = color;
		return this;
	}

	public ShinyTextPathStyle nodeColor(final ShinyColor color) {
		this.nodeColor = color;
		return this;
	}

	public ShinyTextPathStyle leafColor(final ShinyColor color) {
		this.leafColor = color;
		return this;
	}

	public ShinyTextPathStyle separatorColor(final ShinyColor color) {
		this.separatorColor = color;
		return this;
	}
}
