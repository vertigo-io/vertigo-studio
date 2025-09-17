package io.vertigo.shiny.components.dataviz.status;

import java.util.ArrayList;
import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyStatusBuilder implements Builder<ShinyStatus> {
	private String statusTitle;
	private final List<ShinyStatusType> statusTypes = new ArrayList<>();
	private ShinyStatusStyle statusStyle;

	// No public constructor, use ShinyStatus.builder()
	ShinyStatusBuilder() {
		// Package-private constructor
		this.statusStyle = Shiny.theme().statusStyle(); // Initialize default style
	}

	public ShinyStatusBuilder withStyle(final ShinyStatusStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.statusStyle = style;
		return this;
	}

	public ShinyStatusBuilder withTitle(final String title) {
		this.statusTitle = title;
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final List<ShinyStatusType> types) {
		this.statusTypes.addAll(types);
		return this;
	}

	public ShinyStatusBuilder addAllTypes(final ShinyStatusType... types) {
		return addAllTypes(List.of(types));
	}

	@Override
	public ShinyStatus build() {
		return new ShinyStatus(statusTitle, statusTypes, statusStyle);
	}
}
