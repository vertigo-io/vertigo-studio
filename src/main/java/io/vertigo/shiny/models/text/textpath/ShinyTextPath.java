package io.vertigo.shiny.models.text.textpath;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyModel;
import jakarta.annotation.Nonnull;

public record ShinyTextPath(
		@Nonnull String path,
		@Nonnull String separator) implements ShinyModel {

	public ShinyTextPath {
		Assertion.check()
				.isNotBlank(path, "Path is required")
				.isNotBlank(separator, "Separator is required");
	}
}
