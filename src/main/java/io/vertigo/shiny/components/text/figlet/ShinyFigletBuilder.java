package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Builder;

public final class ShinyFigletBuilder implements Builder<ShinyFiglet> {
	private String _text;

	public ShinyFigletBuilder withText(final String text) {
		this._text = text;
		return this;
	}

	@Override
	public ShinyFiglet build() {
		return new ShinyFiglet(_text);
	}
}
