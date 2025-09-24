package io.vertigo.shiny.components.live.progressbar;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.renderers.live.ShinyProgressBarStyle;

public final class ShinyProgressBarBuilder implements Builder<ShinyProgressBar> {
	int total = 0; // Valeur totale correspondant à 100%
	ShinyProgressBarStyle progressBarStyle;

	public ShinyProgressBarBuilder() {
		this.progressBarStyle = Shiny.theme().progressBarStyle(); // Initialize default style
	}

	public ShinyProgressBarBuilder withStyle(final ShinyProgressBarStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.progressBarStyle = style;
		return this;
	}

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
		return new ShinyProgressBar(this);
	}
}
