package io.vertigo.shiny.components.data.json;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyJson(
		String title,
		String json,
		@JsonIgnore ShinyJsonStyle style) implements ShinyComponent {

	public ShinyJson {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
	}

	// Static factory method to get a new Builder instance
	public static ShinyJsonBuilder builder() {
		return new ShinyJsonBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinyJsonRenderer().render(this, writer);
	}
}
