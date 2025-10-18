package io.vertigo.shiny.models.text.title;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTitle(
		UUID id,
		String title,
		int level) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyTitle";
	}

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
