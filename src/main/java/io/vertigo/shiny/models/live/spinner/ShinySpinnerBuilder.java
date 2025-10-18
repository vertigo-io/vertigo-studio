package io.vertigo.shiny.models.live.spinner;

import io.vertigo.core.lang.Builder;

public final class ShinySpinnerBuilder implements Builder<ShinySpinner> {
	private String _message;

	public ShinySpinnerBuilder withMessage(final String message) {
		this._message = message;
		return this;
	}

	@Override
	public ShinySpinner build() {
		// Perform any final validations here before building the object
		//---
		return new ShinySpinner(_message);
	}
}
