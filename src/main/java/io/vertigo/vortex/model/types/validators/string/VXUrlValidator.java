package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;
import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

/**
 * Validator to check if a string is a valid URL.
 */
public record VXUrlValidator() implements VXValidator<String, Void> {

	// A comprehensive regex for URL validation (simplified for demonstration)
	// In a real application, consider a dedicated library or more robust regex.
	private static final Pattern URL_PATTERN = Pattern.compile(
			"^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.isBlank()
				|| URL_PATTERN.matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("Value is not a valid URL.");
	}

	@Override
	public Optional<VxProperty<Void>> getProperty() {
		return Optional.empty();
	}
}
