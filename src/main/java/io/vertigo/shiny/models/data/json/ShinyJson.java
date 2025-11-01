package io.vertigo.shiny.models.data.json;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyJson(
		String title,
		String json) implements ShinyBlock {

	public ShinyJson {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
	}
}
