package io.vertigo.shiny.models.feedback.error;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyErrorBuilder implements Builder<ShinyError> {
	private String _text;

	public ShinyErrorBuilder withText(@Nonnull final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyError build() {
		return new ShinyError(_text);
	}
}
