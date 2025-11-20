package io.vertigo.shiny.models.text.figlet;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyFiglet(
		@Nonnull String text) implements ShinyModel {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
