package io.vertigo.shiny.models.core.container;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyModel;

public final class ShinyContainerBuilder implements Builder<ShinyContainer> {
	private UUID _id;
	private List<ShinyModel> _components = new ArrayList<>();

	public ShinyContainerBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyContainerBuilder addComponent(final ShinyModel component) {
		Assertion.check().isNotNull(component);
		//---
		this._components.add(component);
		return this;
	}

	public ShinyContainerBuilder addAllComponents(final ShinyModel... components) {
		Assertion.check().isNotNull(components);
		//---
		this._components.addAll(List.of(components));
		return this;
	}

	@Override
	public ShinyContainer build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyContainer(_id, _components);
	}
}
