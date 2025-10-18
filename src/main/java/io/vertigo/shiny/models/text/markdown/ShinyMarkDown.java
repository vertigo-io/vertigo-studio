package io.vertigo.shiny.components.text.markdown;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyMarkDown(
		String markdownText) implements ShinyModel {

	// Package-private constructor, only accessible by the Builder
	ShinyMarkDown(ShinyMarkDownBuilder builder) {
		this(builder.markdownText()); // Access builder field directly
	}

	// Static factory method to get a new Builder instance
	public static ShinyMarkDownBuilder builder() {
		return new ShinyMarkDownBuilder();
	}

	@Override
	public void render(final ShinyWriter writer) {
		new ShinyMarkDownRenderer().render(this, writer);
	}

}
