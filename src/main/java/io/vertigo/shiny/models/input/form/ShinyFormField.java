package io.vertigo.shiny.models.input.form;

import java.util.List;

import javax.annotation.Nonnull;

import io.vertigo.core.lang.Assertion;

public record ShinyFormField(
		@Nonnull String name,
		@Nonnull String label,
		@Nonnull ShinyFormFieldType type,
		Object value,
		boolean required,
		String placeholder,
		String helpText,
		Object defaultValue,
		List<ShinyFormOption> options,
		ShinyFormFieldValidator validator,
		boolean readOnly) {

	public ShinyFormField {
		Assertion.check()
				.isNotBlank(name)
				.isNotBlank(label)
				.isNotNull(type);
		// value can be null
		// options can be null if not a SELECT, RADIO, or CHECKBOX_GROUP
	}

	public boolean validate() {
		return validator.validate(value);
	}
}
