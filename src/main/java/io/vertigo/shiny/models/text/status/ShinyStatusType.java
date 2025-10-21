package io.vertigo.shiny.models.text.status;

import io.vertigo.shiny.style.ShinyColor;
import io.vertigo.shiny.style.ShinyColors;

public enum ShinyStatusType {
	SUCCESS(ShinyColors.GREEN),
	ERROR(ShinyColors.RED),
	WARNING(ShinyColors.YELLOW),
	INFO(ShinyColors.BLUE),
	NEUTRAL(ShinyColors.WHITE);

	private final ShinyColor color;

	ShinyStatusType(final ShinyColor color) {
		this.color = color;
	}

	public ShinyColor color() {
		return color;
	}
}
