package io.vertigo.shiny.models.layout.stepper;

import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;

public record ShinyStepper(@Nonnull List<ShinyStep> steps) implements ShinyBlock {
	public ShinyStepper {
		Assertion.check()
				.isNotNull(steps);
	}
}
