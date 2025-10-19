package io.vertigo.shiny.models.text.paragraph;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyParagraph(
		UUID id,
		String text) implements ShinyModel {

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
