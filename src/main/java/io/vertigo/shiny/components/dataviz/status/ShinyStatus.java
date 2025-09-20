package io.vertigo.shiny.components.dataviz.status;

import java.util.List;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyStatus(
		String title,
		List<ShinyStatusType> types,
		ShinyStatusStyle style) implements ShinyComponent {

	public ShinyStatus {
	}

	// Static factory method to get a new Builder instance
	public static ShinyStatusBuilder builder() {
		return new ShinyStatusBuilder();
	}

	public void render(final ShinyWriter writer) {
		ShinyStatusRenderer.render(this, writer);
	}
}
