package io.vertigo.shiny.models.data.form;

import java.util.List;

import io.vertigo.core.lang.Assertion;

public record ShinyFormSection(
		String title,
		List<ShinyFormField> fields,
		boolean collapsible,
		boolean initiallyCollapsed) {
	public ShinyFormSection {
		Assertion.check()
				.isNotBlank(title)
				.isNotNull(fields);
	}
}
