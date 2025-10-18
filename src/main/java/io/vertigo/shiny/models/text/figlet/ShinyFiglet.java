package io.vertigo.shiny.models.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyFiglet(
		String text) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyFiglet";
	}

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
