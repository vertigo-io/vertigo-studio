package io.vertigo.shiny.models.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyTitle(
		@Nonnull String title,
		int level) implements ShinyModel {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title is required");
	}
}
