package io.vertigo.shiny.models.text.textpath;

import java.util.UUID;

import io.vertigo.shiny.models.ShinyModel;

public record ShinyTextPath(
		UUID id,
		String path,
		String separator) implements ShinyModel {

	@Override
	public String shinyType() {
		return "ShinyTextPath";
	}

	public ShinyTextPath {
		Assertion.check().isNotBlank(path, "Path cannot be blank");
	}
}
