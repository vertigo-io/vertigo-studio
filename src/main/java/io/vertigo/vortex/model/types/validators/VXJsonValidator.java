package io.vertigo.vortex.model.types.validators;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

/**
 * Validator to check if a string is a valid JSON.
 */
public record VXJsonValidator() implements VXValidator<String, Void> {

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
	public Optional<VxProperty<Void>> getProperty() {
		return Optional.empty();
	}
}
