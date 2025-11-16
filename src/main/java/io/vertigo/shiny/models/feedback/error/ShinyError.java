package io.vertigo.shiny.models.feedback.error;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyError(
		String text) implements ShinyModel {

	public ShinyError {
	}
}
