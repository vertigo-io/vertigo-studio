package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyParagraphBuilder implements Builder<ShinyParagraph> {
	String paragraphText;

	// No public constructor, use ShinyParagraph.builder()
	ShinyParagraphBuilder() {
		// Package-private constructor
	}

	public ShinyParagraphBuilder withText(final String text) {
		this.paragraphText = text;
		return this;
	}

	@Override
	public ShinyParagraph build() {
		// Perform any final validations here before building the object
		Assertion.check().isNotNull(paragraphText, "Text cannot be null");
		//---
		return new ShinyParagraph(this);
	}
}
