package io.vertigo.shiny.models.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyElement;

public record ShinyFiglet(
		String text) implements ShinyElement {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
