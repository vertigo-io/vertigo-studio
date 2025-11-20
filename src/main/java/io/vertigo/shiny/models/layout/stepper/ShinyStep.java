package io.vertigo.shiny.models.layout.stepper;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyStep(@Nonnull String title, String subtitle, @Nonnull ShinyBlock content) {
	public ShinyStep {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
