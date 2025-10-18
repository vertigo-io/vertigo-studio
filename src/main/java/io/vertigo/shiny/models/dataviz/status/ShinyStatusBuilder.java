package io.vertigo.shiny.models.dataviz.status;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyStatusBuilder implements Builder<ShinyStatus> {
	private UUID _id;
	private String _title;
	private final List<ShinyStatusType> _statusTypes = new ArrayList<>();

	public ShinyStatusBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyStatusBuilder withTitle(final String title) {
		this._title = title;
		return this;
	}

	public ShinyStatusBuilder addType(final ShinyStatusType type) {
		Assertion.check().isNotNull(type);
		//---
		this._statusTypes.add(type);
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final List<ShinyStatusType> types) {
		Assertion.check().isNotNull(types);
		//---
		this._statusTypes.addAll(types);
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final ShinyStatusType... types) {
		Assertion.check().isNotNull(types);
		//---
		return addAllTypes(List.of(types));
	}

	@Override
	public ShinyStatus build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyStatus(_id, _title, _statusTypes);
	}
}
