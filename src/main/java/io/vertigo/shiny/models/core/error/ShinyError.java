package io.vertigo.shiny.models.core.error;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyError(
		String text) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyError";
	}

}
