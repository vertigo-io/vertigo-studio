package io.vertigo.shiny.components.core.container;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.components.ShinyComponent;

public final class ShinyContainerBuilder implements Builder<ShinyContainer> {
	private List<ShinyComponent> _components = new ArrayList<>();

	public ShinyContainerBuilder addComponent(final ShinyComponent component) {
		Assertion.check().isNotNull(component);
		//---
		this._components.add(component);
		return this;
	}

	public ShinyContainerBuilder addAllComponents(final ShinyComponent... components) {
		Assertion.check().isNotNull(components);
		//---
		this._components.addAll(List.of(components));
		return this;
	}

	@Override
	public ShinyContainer build() {
		return new ShinyContainer(_components);
	}
}
