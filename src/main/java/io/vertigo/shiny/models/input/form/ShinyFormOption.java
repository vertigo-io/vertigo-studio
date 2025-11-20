package io.vertigo.shiny.models.input.form;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public record ShinyFormOption(@Nonnull String label, @Nonnull Object value) {
	public ShinyFormOption {
		Assertion.check()
				.isNotBlank(label)
				.isNotNull(value);
	}
}
