package io.vertigo.shiny.models.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import jakarta.annotation.Nonnull;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {

	private String _json;
	private String _title;

	public ShinyJsonBuilder withTitle(@Nonnull final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this._title = title;
		return this;
	}

	public ShinyJsonBuilder withJson(@Nonnull final String json) {
		Assertion.check().isNotBlank(json);
		this._json = json;
		return this;
	}

	@Override
	public ShinyJson build() {

		return new ShinyJson(_title, _json);
	}
}
