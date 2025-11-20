package io.vertigo.shiny.models.layout.page;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyLayout;

public record ShinyPage(@Nonnull String title, @Nonnull ShinyLayout layout) implements ShinyBlock {
	public ShinyPage {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(layout);
	}
}
