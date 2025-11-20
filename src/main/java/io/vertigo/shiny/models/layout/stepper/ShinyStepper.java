package io.vertigo.shiny.models.layout.stepper;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyStepper(@Nonnull List<ShinyStep> steps) implements ShinyBlock {
	public ShinyStepper {
		Assertion.check()
				.isNotNull(steps);
	}
}
