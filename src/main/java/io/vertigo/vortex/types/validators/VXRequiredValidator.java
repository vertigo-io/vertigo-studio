package io.vertigo.vortex.types.validators;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.types.VXValidator;
import io.vertigo.vortex.types.VxProperty;
import io.vertigo.vortex.types.VxProps;

/**
 * Validator to check for non-null and non-empty values.
 * For Strings, it checks that the string is not blank.
 * For other Objects, it checks that the object is not null.
 * @synthetic
 */
public record VXRequiredValidator() implements VXValidator<Object, Boolean> {

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

	@Override
	public Optional<VxProperty<Boolean>> getProperty() {
		// A required field can be represented by a boolean 'true'
		return Optional.of(new VxProperty<>(VxProps.REQUIRED, Boolean.class));
	}
}
