package io.vertigo.shiny.models.input.form;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyFormOption(@Nonnull String label, @Nonnull Object value) {
	public ShinyFormOption {
		Assertion.check()
				.isNotBlank(label)
				.isNotNull(value);
	}
}
