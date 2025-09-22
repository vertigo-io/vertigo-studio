package io.vertigo.shiny.components.core.container;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyContainerBuilder implements Builder<ShinyContainer> {
	private List<ShinyComponent> containerComponents = new ArrayList<>();

	public ShinyContainerBuilder addComponent(final ShinyComponent component) {
		Assertion.check().isNotNull(component);
		//---
		this.containerComponents.add(component);
		return this;
	}

	public ShinyContainerBuilder addAllComponents(final ShinyComponent... components) {
		Assertion.check().isNotNull(components);
		//---
		this.containerComponents.addAll(containerComponents);
		return this;
	}

	@Override
	public ShinyContainer build() {
		return new ShinyContainer(containerComponents);
	}
}
