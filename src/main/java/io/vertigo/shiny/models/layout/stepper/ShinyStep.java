package io.vertigo.shiny.models.layout.stepper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyStep(@Nonnull String title, String subtitle, @Nonnull ShinyBlock content) {
	public ShinyStep {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
