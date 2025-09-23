package io.vertigo.shiny.components.text.figlet;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

public record ShinyFiglet(
		String text,
		@JsonIgnore ShinyFigletStyle style) implements ShinyComponent {

	public ShinyFiglet {
		Assertion.check().isNotNull(text);
	}
}
