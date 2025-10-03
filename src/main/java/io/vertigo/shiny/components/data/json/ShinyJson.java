package io.vertigo.shiny.components.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.components.ShinyComponent;

import io.vertigo.shiny.ShinyType;

@ShinyType("json")
public record ShinyJson(
		String title,
		String json) implements ShinyComponent {

	public ShinyJson {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
	}
}
