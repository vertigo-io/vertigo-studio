package io.vertigo.shiny.components.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyProgressBarBuilder implements Builder<ShinyProgressBar> {
	private int total = 0; // Valeur totale correspondant à 100%

	public ShinyProgressBarBuilder withTotal(final int value) {
		Assertion.check().isTrue(value > 0, "total must be > 0");
		//---
		this.total = value;
		return this;
	}

	@Override
	public ShinyProgressBar build() {
		// Perform any final validations here before building the object
		//---
		return new ShinyProgressBar(total);
	}
}
