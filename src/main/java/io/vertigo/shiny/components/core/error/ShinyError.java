package io.vertigo.shiny.components.core.error;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyError(
		String text) implements ShinyComponent {

	@Override
	public String type() {
		return "error";
	}

}
