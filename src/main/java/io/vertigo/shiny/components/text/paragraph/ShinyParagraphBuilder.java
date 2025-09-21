package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Builder;

public final class ShinyParagraphBuilder implements Builder<ShinyParagraph> {
	private String paragraphText;

	public ShinyParagraphBuilder withText(final String text) {
		this.paragraphText = text;
		return this;
	}

	@Override
	public ShinyParagraph build() {
		return new ShinyParagraph(paragraphText);
	}
}
