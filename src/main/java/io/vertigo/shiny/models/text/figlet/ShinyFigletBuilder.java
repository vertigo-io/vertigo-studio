package io.vertigo.shiny.models.text.figlet;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyFigletBuilder implements Builder<ShinyFiglet> {

	private String _text;



	public ShinyFigletBuilder withText(final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyFiglet build() {

		return new ShinyFiglet(null, _text);
	}
}
