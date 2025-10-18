package io.vertigo.shiny.models.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {
	private String _json;
	private String _title;

	public ShinyJsonBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyJsonBuilder withJson(final String json) {
		this._json = json;
		return this;
	}

	@Override
	public ShinyJson build() {
		return new ShinyJson(_title, _json);
	}
}
