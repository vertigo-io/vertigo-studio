package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;
import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

/**
 * Validator to check if a string is a valid URL slug.
 */
public record VXSlugValidator() implements VXValidator<String, Void> {

	// A slug typically consists of lowercase letters, numbers, and hyphens.
	private static final Pattern SLUG_PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| SLUG_PATTERN.matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("Value is not a valid URL slug. It should only contain lowercase letters, numbers, and hyphens, and not start or end with a hyphen.");
	}

	@Override
	public Optional<VxProperty<Void>> getProperty() {
		return Optional.empty();
	}
}
