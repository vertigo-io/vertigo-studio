package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTitleBuilder implements Builder<ShinyTitle> {
	String title;
	int level = 1; // Default to Level 1

	// No public constructor, use ShinyTitle.builder()
	ShinyTitleBuilder() {
		// Package-private constructor
	}

	public ShinyTitleBuilder withText(final String text) {
		this.title = text;
		return this;
	}

	public ShinyTitleBuilder withLevel(final int titleLevel) {
		Assertion.check()
				.isTrue(titleLevel >= 1 && titleLevel <= 3, "Title level must be between 1 and 3");
		this.level = titleLevel;
		return this;
	}

	@Override
	public ShinyTitle build() {
		// Perform any final validations here before building the object
		Assertion.check().isNotBlank(title, "Title cannot be blank");
		//---
		return new ShinyTitle(this);
	}
}
