package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyParagraph implements ShinyComponent {
	private String paragraphText;

	public ShinyParagraph() {
	}

	public ShinyParagraph withText(final String text) {
		this.paragraphText = text;
		return this;
	}

	@Override
	public void render(final ShinyWriter writer) {
		Assertion.check().isNotNull(paragraphText, "Text cannot be null");
		//---
		writer.println(paragraphText);
		writer.println(); // Add a blank line after the paragraph
	}
}