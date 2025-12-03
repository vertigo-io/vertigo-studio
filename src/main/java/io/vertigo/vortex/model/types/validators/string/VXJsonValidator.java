package io.vertigo.vortex.model.types.validators.string;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

/**
 * Validator to check if a string is a valid JSON.
 */
public record VXJsonValidator() implements VXValidator<String, Boolean> {

	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	@Override
	public boolean isValid(final String value) {
		if (value != null && !value.isEmpty()) {
			try {
				OBJECT_MAPPER.readTree(value);
			} catch (final Exception e) {
				return false;
			}
		}
		return true;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("Value is not a valid JSON.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProperty.of(VXProps.JSON, true);
	}
}
