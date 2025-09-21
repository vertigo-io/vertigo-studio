package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyComponentRenderer; // New import
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent; // New import

public final class ShinyParagraphRenderer implements ShinyComponentRenderer<ShinyParagraph> { // Implements interface

	@Override
	public boolean accept(final ShinyComponent component) {
		return component instanceof ShinyParagraph;
	}

	@Override
	public void render(final ShinyParagraph shinyParagraph, final ShinyWriter writer) { // Not static
		Assertion.check().isNotNull(shinyParagraph);
		Assertion.check().isNotNull(writer);
		//---
		writer.println(shinyParagraph.text());
		writer.println(); // Add a blank line after the paragraph
	}
}
