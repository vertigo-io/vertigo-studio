package io.vertigo.shiny.models.text.status;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyStatusBuilder implements Builder<ShinyStatus> {

	private String _title;
	private final List<ShinyStatusType> _statusTypes = new ArrayList<>();

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

		return new ShinyStatus(null, _title, _statusTypes);
	}
}
