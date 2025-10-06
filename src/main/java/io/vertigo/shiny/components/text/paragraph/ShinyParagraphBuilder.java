package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Builder;

public final class ShinyParagraphBuilder implements Builder<ShinyParagraph> {
	private String _text;

	public ShinyParagraphBuilder withText(final String text) {
		this._text = text;
		return this;
	}

	@Override
	public ShinyParagraph build() {
		return new ShinyParagraph(_text);
	}
}
