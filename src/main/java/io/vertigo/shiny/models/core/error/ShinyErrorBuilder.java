package io.vertigo.shiny.models.core.error;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyErrorBuilder implements Builder<ShinyError> {

	private String _text;



	public ShinyErrorBuilder withText(final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyError build() {

		return new ShinyError(null, _text);
	}
}
