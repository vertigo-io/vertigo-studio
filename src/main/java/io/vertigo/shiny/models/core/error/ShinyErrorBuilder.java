package io.vertigo.shiny.models.core.error;

import io.vertigo.core.lang.Builder;

public final class ShinyErrorBuilder implements Builder<ShinyError> {
	private String _text;

	public ShinyErrorBuilder withText(final String text) {
		this._text = text;
		return this;
	}

	@Override
	public ShinyError build() {
		return new ShinyError(_text);
	}
}
