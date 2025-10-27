package io.vertigo.shiny.models.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyParagraph(
		String text) implements ShinyModel {

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
