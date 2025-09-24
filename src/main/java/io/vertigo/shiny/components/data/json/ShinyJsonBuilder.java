package io.vertigo.shiny.components.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {
	private String jsonString;
	private String jsonTitle;

	public ShinyJsonBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.jsonTitle = title;
		return this;
	}

	public ShinyJsonBuilder withJson(final String json) {
		this.jsonString = json;
		return this;
	}

	@Override
	public ShinyJson build() {
		return new ShinyJson(jsonTitle, jsonString);
	}
}
