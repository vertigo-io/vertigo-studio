package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinySpinnerBuilder implements Builder<ShinySpinner> {
	String message;
	ShinySpinnerStyle spinnerStyle;

	public ShinySpinnerBuilder() {
		this.spinnerStyle = Shiny.theme().spinnerStyle(); // Initialize default style
	}

	public ShinySpinnerBuilder withStyle(final ShinySpinnerStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.spinnerStyle = style;
		return this;
	}

	public ShinySpinnerBuilder withMessage(final String msg) {
		this.message = msg;
		return this;
	}

	@Override
	public ShinySpinner build() {
		// Perform any final validations here before building the object
		//---
		return new ShinySpinner(this);
	}
}
