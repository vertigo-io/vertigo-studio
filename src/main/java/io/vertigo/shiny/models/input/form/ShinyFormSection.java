package io.vertigo.shiny.models.input.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;
import jakarta.annotation.Nonnull;

public record ShinyFormSection(
		@Nonnull String title,
		@Nonnull List<ShinyFormField> fields,
		boolean collapsible,
		boolean initiallyCollapsed) {
	public ShinyFormSection {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(fields);
	}
}
