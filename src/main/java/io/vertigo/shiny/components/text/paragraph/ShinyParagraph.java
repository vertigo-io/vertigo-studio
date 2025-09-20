package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyParagraph(
		String text) implements ShinyComponent {

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}

	// Static factory method to get a new Builder instance
	public static ShinyParagraphBuilder builder() {
		return new ShinyParagraphBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyParagraphRenderer().render(this, writer);
	}
}
