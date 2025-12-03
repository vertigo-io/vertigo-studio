package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXExactLengthValidator(int length) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.length() == length;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The length must be exactly " + length + " characters.");
	}

	@Override
	public Optional<VxProperty<Integer>> getProperty() {
		// This doesn't fit into a standard VxProps property, so we return empty.
		// A custom property could be created if needed on the client-side.
		return Optional.empty();
	}
}
