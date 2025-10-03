package io.vertigo.shiny.components.core.container;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("container")
public record ShinyContainer(
		List<ShinyComponent> components) implements ShinyComponent {
}
