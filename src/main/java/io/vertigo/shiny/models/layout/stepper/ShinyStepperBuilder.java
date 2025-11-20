package io.vertigo.shiny.models.layout.stepper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public final class ShinyStepperBuilder {
	private final List<ShinyStep> _steps = new ArrayList<>();

	public ShinyStepperBuilder addStep(final String title, final ShinyBlock content) {
		return addStep(title, null, content);
	}

	public ShinyStepperBuilder addStep(@Nonnull final String title, final String subtitle, @Nonnull final ShinyBlock content) {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(content);
		//---
		_steps.add(new ShinyStep(title, subtitle, content));
		return this;
	}

	public ShinyStepper build() {
		return new ShinyStepper(_steps);
	}
}
