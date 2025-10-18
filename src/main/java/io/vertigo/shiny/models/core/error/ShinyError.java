package io.vertigo.shiny.models.core.error;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyError(
		UUID id,
		String text) implements ShinyModel {

	public ShinyError {
		Assertion.check().isNotNull(id);
	}

	@Override
	public String shinyType() {
		return "ShinyError";
	}

}
