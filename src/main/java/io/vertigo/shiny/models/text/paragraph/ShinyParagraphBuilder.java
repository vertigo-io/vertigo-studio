package io.vertigo.shiny.models.text.paragraph;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyParagraphBuilder implements Builder<ShinyParagraph> {
	private UUID _id;
	private String _text;

	public ShinyParagraphBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyParagraphBuilder withText(final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyParagraph build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyParagraph(_id, _text);
	}
}
