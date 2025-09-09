package io.vertigo.shiny.components.dataviz.status;

import io.vertigo.core.lang.Assertion;

public final class ShinyStatusStyle {
	private ShinyStatusShape statusShape = ShinyStatusShape.SQUARE;

	public ShinyStatusStyle shape(final ShinyStatusShape shape) {
		Assertion.check().isNotNull(shape);
		//---
		this.statusShape = shape;
		return this;
	}

	ShinyStatusShape shape() {
		return statusShape;
	}
}
