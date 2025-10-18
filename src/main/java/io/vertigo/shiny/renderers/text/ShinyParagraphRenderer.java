package io.vertigo.shiny.renderers.text;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.Shiny;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;
import io.vertigo.shiny.models.text.paragraph.ShinyParagraph;
import io.vertigo.shiny.renderers.ShinyComponentRenderer;

public final class ShinyParagraphRenderer implements ShinyComponentRenderer<ShinyParagraph> {

	@Override
	public boolean accept(final ShinyModel component) {
		return component instanceof ShinyParagraph;
	}

	@Override
	public void render(final ShinyParagraph shinyParagraph) {
		Assertion.check()
				.isNotNull(shinyParagraph);
		//---
		//		final ShinyParagraphStyle style = Shiny.theme().paragraphStyle();
		final ShinyWriter writer = Shiny.writer();

		writer.println(shinyParagraph.text());
		writer.println(); // Add a blank line after the paragraph
	}
}
