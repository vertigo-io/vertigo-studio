package io.vertigo.shiny.components.live.spinner;

import io.vertigo.core.lang.Builder;

public final class ShinySpinnerBuilder implements Builder<ShinySpinner> {
	String message;

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
