package io.vertigo.shiny.components.error;

import io.vertigo.core.lang.Builder;

public final class ShinyErrorBuilder implements Builder<ShinyError> {
	private String errorText;

	public ShinyErrorBuilder withText(final String text) {
		this.errorText = text;
		return this;
	}

	@Override
	public ShinyError build() {
		return new ShinyError(errorText);
	}
}
