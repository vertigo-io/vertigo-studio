package io.vertigo.shiny.models.text.title;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyTitleBuilder implements Builder<ShinyTitle> {
	private UUID _id;
	private String _text;
	private int _level = 1; // Default to Level 1

	public ShinyTitleBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyTitleBuilder withText(final String text) {
		Assertion.check().isNotBlank(text);
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
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyTitle(_id, _text, _level);
	}
}
