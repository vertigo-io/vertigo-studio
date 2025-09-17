package io.vertigo.shiny.components.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.core.lang.Builder;
import io.vertigo.shiny.Shiny;

public final class ShinyJsonBuilder implements Builder<ShinyJson> {
	String jsonString;
	ShinyJsonStyle jsonStyle;

	// No public constructor, use ShinyJson.builder()
	ShinyJsonBuilder() {
		// Package-private constructor
		jsonStyle = Shiny.theme().jsonStyle(); // Initialize default style
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
		// Perform any final validations here before building the object
		Assertion.check().isNotBlank(jsonString, "JSON string cannot be blank");
		//---
		return new ShinyJson(this);
	}
}
