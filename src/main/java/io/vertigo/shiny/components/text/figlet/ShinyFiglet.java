package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyFiglet(
		String text) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyFiglet";
	}

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
