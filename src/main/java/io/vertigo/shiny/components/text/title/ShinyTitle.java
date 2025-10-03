package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("title")
public record ShinyTitle(
		String title,
		int level) implements ShinyComponent {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
