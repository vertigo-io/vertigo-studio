package io.vertigo.shiny.components.text.title;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTitleBuilder implements Builder<ShinyTitle> {
	private String _text;
	private int _level = 1; // Default to Level 1

	public ShinyTitleBuilder withText(final String text) {
		this._text = text;
		return this;
	}

	public ShinyTitleBuilder withLevel(final int level) {
		Assertion.check()
				.isTrue(level >= 1 && level <= 3, "Title level must be between 1 and 3");
		this._level = level;
		return this;
	}

	@Override
	public ShinyTitle build() {
		return new ShinyTitle(_text, _level);
	}
}
