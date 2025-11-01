package io.vertigo.shiny.models.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyElement;

public record ShinyTitle(
		String title,
		int level) implements ShinyElement {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title is required");
	}
}
