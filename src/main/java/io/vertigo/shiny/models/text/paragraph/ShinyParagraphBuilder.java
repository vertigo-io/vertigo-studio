package io.vertigo.shiny.models.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyParagraphBuilder implements Builder<ShinyParagraph> {
	private String _text;

	public ShinyParagraphBuilder withText(@Nonnull final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyParagraph build() {
		return new ShinyParagraph(_text);
	}
}
