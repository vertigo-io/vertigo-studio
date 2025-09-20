package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;
import io.vertigo.shiny.style.ShinyColors;

public record ShinyTitle(
		String title,
		int level) implements ShinyComponent {

	public ShinyTitle {
		Assertion.check().isNotBlank(title, "Title cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyTitleBuilder builder() {
		return new ShinyTitleBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyTitleRenderer.render(this, writer);
	}
}
