package io.vertigo.vortex.gold.library.validators.string;

import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

/**
 * Validator to check if a string is a valid URL.
 */
public record VXUrlValidator() implements VXValidator<String, Boolean> {

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
	public VXProperty<Boolean> getProperty() {
		return VXProps.URL.build(true);
	}
}
