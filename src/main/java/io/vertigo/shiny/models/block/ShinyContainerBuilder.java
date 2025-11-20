package io.vertigo.shiny.models.block;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.models.ShinyModel;

public final class ShinyContainerBuilder implements Builder<ShinyContainer> {
	private UUID _id;
	private List<ShinyModel> _models = new ArrayList<>();

	public ShinyContainerBuilder withId(@Nonnull final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyContainerBuilder addModel(@Nonnull final ShinyModel component) {
		Assertion.check().isNotNull(component);
		//---
		this._models.add(component);
		return this;
	}

	public ShinyContainerBuilder addAllModels(@Nonnull final ShinyModel... components) {
		Assertion.check().isNotNull(components);
		//---
		this._models.addAll(List.of(components));
		return this;
	}

	@Override
	public ShinyContainer build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyContainer(_id, _models);
	}
}
