package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyParagraph implements ShinyComponent {
	private final String paragraphText;

	// Package-private constructor, only accessible by the Builder
	ShinyParagraph(ShinyParagraphBuilder builder) {
		Assertion.check()
			.isNotNull(builder);
		//---
		this.paragraphText = builder.paragraphText;
	}

	// Static factory method to get a new Builder instance
	public static ShinyParagraphBuilder builder() {
		return new ShinyParagraphBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		Assertion.check().isNotNull(paragraphText, "Text cannot be null");
		//---
		writer.println(paragraphText);
		writer.println(); // Add a blank line after the paragraph
	}
}
