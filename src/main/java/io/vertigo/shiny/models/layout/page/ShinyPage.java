package io.vertigo.shiny.models.layout.page;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import io.vertigo.shiny.models.ShinyLayout;
import jakarta.annotation.Nonnull;

public record ShinyPage(@Nonnull String title, @Nonnull ShinyLayout layout) implements ShinyBlock {
	public ShinyPage {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(layout);
	}
}
