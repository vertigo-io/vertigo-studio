package io.vertigo.shiny.models.text.markdown;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyMarkDown(@Nonnull String markdownText) implements ShinyModel {

	public ShinyMarkDown {
		Assertion.check().isNotNull(markdownText);
	}

}
