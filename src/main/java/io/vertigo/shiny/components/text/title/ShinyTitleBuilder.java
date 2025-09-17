package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTitleBuilder implements Builder<ShinyTitle> {
	private String titleText;
	private int titleLevel = 1; // Default to Level 1

	// No public constructor, use ShinyTitle.builder()
	ShinyTitleBuilder() {
		// Package-private constructor
	}

	public ShinyTitleBuilder withText(final String text) {
		this.titleText = text;
		return this;
	}

	public ShinyTitleBuilder withLevel(final int level) {
		Assertion.check()
				.isTrue(level >= 1 && level <= 3, "Title level must be between 1 and 3");
		this.titleLevel = level;
		return this;
	}

	@Override
	public ShinyTitle build() {
		return new ShinyTitle(titleText, titleLevel);
	}
}
