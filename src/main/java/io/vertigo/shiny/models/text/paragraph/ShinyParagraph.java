package io.vertigo.shiny.models.text.paragraph;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyParagraph(
		@Nonnull String text) implements ShinyModel {

	public ShinyParagraph {
		Assertion.check().isNotNull(text, "Text cannot be null");
	}
}
