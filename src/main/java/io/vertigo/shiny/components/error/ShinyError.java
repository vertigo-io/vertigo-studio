package io.vertigo.shiny.components.error;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyError(
		String text) implements ShinyComponent {

	public ShinyError {
	}

	// Static factory method to get a new Builder instance
	public static ShinyErrorBuilder builder() {
		return new ShinyErrorBuilder();
	}

	public void render(ShinyWriter writer) {
		new ShinyErrorRenderer().render(this, writer);
	}
}
