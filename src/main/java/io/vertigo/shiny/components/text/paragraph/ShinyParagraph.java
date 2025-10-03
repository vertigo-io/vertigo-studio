package io.vertigo.shiny.components.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("paragraph")
public record ShinyParagraph(
		String text) implements ShinyComponent {

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
