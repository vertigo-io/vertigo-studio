package io.vertigo.shiny.models.text.figlet;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyFiglet(
		UUID id,
		String text) implements ShinyModel {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
