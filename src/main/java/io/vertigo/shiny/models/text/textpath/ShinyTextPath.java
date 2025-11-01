package io.vertigo.shiny.models.text.textpath;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyElement;

public record ShinyTextPath(
		String path,
		String separator) implements ShinyElement {

	public ShinyTextPath {
		Assertion.check()
				.isNotBlank(path, "Path is required")
				.isNotBlank(separator, "Separator is required");
	}
}
