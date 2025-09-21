package io.vertigo.shiny.components.error;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyError(
		String text) implements ShinyComponent {
}
