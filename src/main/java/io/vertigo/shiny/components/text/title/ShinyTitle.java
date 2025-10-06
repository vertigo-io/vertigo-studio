package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTitle(
		String title,
		int level) implements ShinyComponent {

	@Override
	public String type() {
		return "title";
	}


	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}
}
