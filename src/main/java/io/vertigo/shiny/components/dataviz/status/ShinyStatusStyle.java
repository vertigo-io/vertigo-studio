package io.vertigo.shiny.components.dataviz.status;

public final class ShinyStatusStyle {
	public ShinyStatus.StatusShape statusShape = ShinyStatus.StatusShape.SQUARE;

	public ShinyStatusStyle shape(final ShinyStatus.StatusShape shape) {
		this.statusShape = shape;
		return this;
	}
}
