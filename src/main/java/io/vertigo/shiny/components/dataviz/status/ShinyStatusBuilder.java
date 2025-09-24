package io.vertigo.shiny.components.dataviz.status;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyStatusBuilder implements Builder<ShinyStatus> {
	private String statusTitle;
	private final List<ShinyStatusType> statusTypes = new ArrayList<>();

	public ShinyStatusBuilder withTitle(final String title) {
		this.statusTitle = title;
		return this;
	}

	public ShinyStatusBuilder addType(final ShinyStatusType type) {
		Assertion.check().isNotNull(type);
		//---
		this.statusTypes.add(type);
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final List<ShinyStatusType> types) {
		Assertion.check().isNotNull(types);
		//---
		this.statusTypes.addAll(types);
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final ShinyStatusType... types) {
		Assertion.check().isNotNull(types);
		//---
		return addAllTypes(List.of(types));
	}

	@Override
	public ShinyStatus build() {
		return new ShinyStatus(statusTitle, statusTypes);
	}
}
