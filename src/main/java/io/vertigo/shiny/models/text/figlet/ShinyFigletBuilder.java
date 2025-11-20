package io.vertigo.shiny.models.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyFigletBuilder implements Builder<ShinyFiglet> {
	private String _text;

	public ShinyFigletBuilder withText(@Nonnull final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyFiglet build() {

		return new ShinyFiglet(_text);
	}
}
