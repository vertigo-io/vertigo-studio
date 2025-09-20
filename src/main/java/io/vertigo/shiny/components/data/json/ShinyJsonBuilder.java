package io.vertigo.shiny.components.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {
	private String jsonString;
	private ShinyJsonStyle jsonStyle;
	private String jsonTitle;

	public ShinyJsonBuilder() {
		jsonStyle = Shiny.theme().jsonStyle(); // Initialize default style
	}

	public ShinyJsonBuilder withTitle(final String title) {
		Assertion.check().isNotBlank(title);
		//---
		this.jsonTitle = title;
		return this;
	}

	public ShinyJsonBuilder withStyle(final ShinyJsonStyle style) {
		Assertion.check().isNotNull(style);
		//---
		this.jsonStyle = style;
		return this;
	}

	public ShinyJsonBuilder withJson(final String json) {
		this.jsonString = json;
		return this;
	}

	@Override
	public ShinyJson build() {
		return new ShinyJson(jsonTitle, jsonString, jsonStyle);
	}
}
