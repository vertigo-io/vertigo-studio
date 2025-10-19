package io.vertigo.shiny.models.text.textpath;

import java.util.UUID;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTextPath(
		UUID id,
		String path,
		String separator) implements ShinyModel {

	public ShinyTextPath {
		Assertion.check()
				.isNotBlank(path, "Path is required")
				.isNotBlank(separator, "Separator is required");
	}
}
