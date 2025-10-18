package io.vertigo.shiny.models.text.paragraph;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyParagraph(
		UUID id,
		String text) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyParagraph";
	}

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
