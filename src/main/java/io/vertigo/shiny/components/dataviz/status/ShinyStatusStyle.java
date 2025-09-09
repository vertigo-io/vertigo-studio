package io.vertigo.shiny.components.dataviz.status;

import io.vertigo.core.lang.Assertion;

public final class ShinyStatusStyle {
	public ShinyStatus.StatusShape statusShape = ShinyStatus.StatusShape.SQUARE;

	public ShinyStatusStyle shape(final ShinyStatus.StatusShape shape) {
		Assertion.check().isNotNull(shape);
		//---
		this.statusShape = shape;
		return this;
	}
}
