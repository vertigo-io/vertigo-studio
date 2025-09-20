package io.vertigo.shiny.components.data.list;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.shiny.ShinyWriter;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyList(
		String title,
		List<Object> items,
		ShinyListType type,
		@JsonIgnore ShinyListStyle style) implements ShinyComponent {

	public ShinyList {
	}

	// Static factory method to get a new Builder instance
	public static ShinyListBuilder builder() {
		return new ShinyListBuilder();
	}

	public void render(final ShinyWriter writer) {
		new ShinyListRenderer().render(this, writer);
	}
}
