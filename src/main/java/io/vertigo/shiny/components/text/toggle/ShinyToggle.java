package io.vertigo.shiny.components.text.toggle;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyToggle(
		String label,
		boolean value,
		ShinyToggleType type,
		String onText,
		String offText,
		boolean showText,
		ShinyToggleStyle style) implements ShinyComponent {

	public ShinyToggle {
	}

	// Static factory method to get a new Builder instance
	public static ShinyToggleBuilder builder() {
		return new ShinyToggleBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyToggleRenderer.render(this, writer);
	}
}
