package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyFiglet(
		String text,
		ShinyFigletStyle style) implements ShinyComponent {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}

	// Static factory method to get a new Builder instance
	public static ShinyFigletBuilder builder() {
		return new ShinyFigletBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyFigletRenderer.render(this, writer);
	}
}
