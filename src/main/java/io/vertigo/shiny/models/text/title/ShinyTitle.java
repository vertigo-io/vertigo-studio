package io.vertigo.shiny.models.text.title;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTitle(
		@Nonnull String title,
		int level) implements ShinyModel {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title is required");
	}
}
