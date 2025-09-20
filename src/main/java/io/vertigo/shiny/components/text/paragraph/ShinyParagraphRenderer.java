package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;

public final class ShinyParagraphRenderer {

	private ShinyParagraphRenderer() {
		//private constructor
	}

	public static void render(final ShinyParagraph shinyParagraph, final ShinyWriter writer) {
		Assertion.check().isNotNull(shinyParagraph);
		Assertion.check().isNotNull(writer);
		//---
		writer.println(shinyParagraph.text());
		writer.println(); // Add a blank line after the paragraph
	}
}
