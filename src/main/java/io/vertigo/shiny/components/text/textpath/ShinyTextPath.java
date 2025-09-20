package io.vertigo.shiny.components.text.textpath;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyTextPath(
		String path,
		String separator,
		@JsonIgnore ShinyTextPathStyle textPathStyle) implements ShinyComponent {

	public ShinyTextPath {
		// Perform any final validations here before building the object
		Assertion.check().isNotBlank(path, "Path cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyTextPathBuilder builder() {
		return new ShinyTextPathBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinyTextPathRenderer().render(this, writer);
	}
}
