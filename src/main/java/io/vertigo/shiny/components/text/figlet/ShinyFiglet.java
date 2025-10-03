package io.vertigo.shiny.components.text.figlet;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("figlet")
public record ShinyFiglet(
		String text) implements ShinyComponent {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
