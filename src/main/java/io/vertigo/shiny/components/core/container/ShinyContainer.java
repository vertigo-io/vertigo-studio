package io.vertigo.shiny.components.core.container;

import java.util.List;

import io.vertigo.shiny.components.ShinyComponent;

public record ShinyContainer(
		List<ShinyComponent> components) implements ShinyComponent {

	@Override
	public String type() {
		return "ShinyContainer";
	}

}
