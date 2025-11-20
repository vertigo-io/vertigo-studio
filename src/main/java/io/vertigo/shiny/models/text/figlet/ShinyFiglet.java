package io.vertigo.shiny.models.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyFiglet(
		@Nonnull String text) implements ShinyModel {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
