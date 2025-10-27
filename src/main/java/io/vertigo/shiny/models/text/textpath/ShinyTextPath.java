package io.vertigo.shiny.models.text.textpath;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTextPath(
		String path,
		String separator) implements ShinyModel {

	public ShinyTextPath {
		Assertion.check()
				.isNotBlank(path, "Path is required")
				.isNotBlank(separator, "Separator is required");
	}
}
