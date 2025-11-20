package io.vertigo.shiny.models.text.markdown;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyMarkDown(@Nonnull String markdownText) implements ShinyModel {

	public ShinyMarkDown {
		Assertion.check().isNotNull(markdownText);
	}

}
