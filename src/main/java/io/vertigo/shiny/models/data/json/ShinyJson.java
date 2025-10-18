package io.vertigo.shiny.models.data.json;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyJson(
		UUID id,
		String title,
		String json) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyJson";
	}

	public ShinyJson {
		Assertion.check().isNotBlank(json, "JSON string cannot be blank");
	}
}
