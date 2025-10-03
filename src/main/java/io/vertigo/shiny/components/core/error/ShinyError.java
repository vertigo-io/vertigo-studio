package io.vertigo.shiny.components.core.error;

import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("error")
public record ShinyError(
		String text) implements ShinyComponent {
}
