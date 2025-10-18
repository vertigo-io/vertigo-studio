package io.vertigo.shiny.models.text.figlet;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyFigletBuilder implements Builder<ShinyFiglet> {
	private UUID _id;
	private String _text;

	public ShinyFigletBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyFigletBuilder withText(final String text) {
		Assertion.check().isNotNull(text);
		this._text = text;
		return this;
	}

	@Override
	public ShinyFiglet build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyFiglet(_id, _text);
	}
}
