package io.vertigo.shiny.components.form;

import io.vertigo.core.lang.Assertion;

public record ShinyFormField(String name, String label, ShinyFormFieldType type, Object value) {
	public ShinyFormField {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(label)
				.isNotNull(type);
		// value can be null
	}
}
