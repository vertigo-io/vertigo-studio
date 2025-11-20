package io.vertigo.shiny.models.text.textpath;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;

public record ShinyTextPath(
		@Nonnull String path,
		@Nonnull String separator) implements ShinyModel {

	public ShinyTextPath {
		Assertion.check()
				.isNotBlank(path, "Path is required")
				.isNotBlank(separator, "Separator is required");
	}
}
