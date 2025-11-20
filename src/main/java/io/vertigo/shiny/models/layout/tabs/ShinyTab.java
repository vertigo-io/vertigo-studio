package io.vertigo.shiny.models.layout.tabs;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyTab(@Nonnull String title, @Nonnull ShinyBlock content) {
	public ShinyTab {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
