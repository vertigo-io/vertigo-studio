package io.vertigo.shiny.models.layout.tabs;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyTab(@Nonnull String title, @Nonnull ShinyBlock content) {
	public ShinyTab {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
