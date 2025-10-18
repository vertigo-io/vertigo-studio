package io.vertigo.shiny.models.core.error;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyErrorBuilder implements Builder<ShinyError> {
	private UUID _id;
	private String _text;

	public ShinyErrorBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyErrorBuilder withText(final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyError build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyError(_id, _text);
	}
}
