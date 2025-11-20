package io.vertigo.shiny.models.input.datepicker;

import io.vertigo.core.lang.Assertion;
import io.vertigo.shiny.models.ShinyBlock;
import jakarta.annotation.Nonnull;

public record ShinyDatePicker(@Nonnull String label, String value, boolean required) implements ShinyBlock {
	public ShinyDatePicker {
		Assertion.check()
				.isNotBlank(label);
	}
}
