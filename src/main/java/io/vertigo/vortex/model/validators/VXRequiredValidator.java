package io.vertigo.vortex.model.validators;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.VXValidator;

/**
 * Validator to check for non-null and non-empty values.
 * For Strings, it checks that the string is not blank.
 * For other Objects, it checks that the object is not null.
 * @synthetic
 */
public record VXRequiredValidator() implements VXValidator<Object> {

	/** {@inheritDoc} */
	@Override
	public boolean isValid(final Object value) {
		if (value instanceof String str) {
			return !str.isBlank();
		}
		return value != null;
	}

	/** {@inheritDoc} */
	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("This field is required");
	}
}
