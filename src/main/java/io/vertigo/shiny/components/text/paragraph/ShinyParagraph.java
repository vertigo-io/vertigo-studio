package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyParagraph implements ShinyComponent {
	private final String text;

	public ShinyParagraph(final Shiny shiny, final String text) {
		Assertion.check().isNotNull(text);
		//---
		this.text = text;
	}

	@Override
	public void render(final ShinyWriter writer) {
		writer.println(text)
				.println(); // Add a blank line after the paragraph
	}
}
