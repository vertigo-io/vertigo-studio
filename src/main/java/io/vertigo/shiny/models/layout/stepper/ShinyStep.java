package io.vertigo.shiny.models.layout.stepper;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyStep(String title, String subtitle, ShinyBlock content) {
	public ShinyStep {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
	}
}
