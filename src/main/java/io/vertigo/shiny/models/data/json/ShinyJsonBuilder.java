package io.vertigo.shiny.models.data.json;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {
	private UUID _id;
	private String _json;
	private String _title;

	public ShinyJsonBuilder withId(final UUID id) {
		Assertion.check().isNotNull(id);
		//---
		_id = id;
		return this;
	}

	public ShinyJsonBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyJsonBuilder withJson(final String json) {
		Assertion.check().isNotBlank(json);
		this._json = json;
		return this;
	}

	@Override
	public ShinyJson build() {
		_id = _id == null ? UUID.randomUUID() : _id;
		return new ShinyJson(_id, _title, _json);
	}
}
