package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyParagraph(
		String text) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyParagraph";
	}

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
