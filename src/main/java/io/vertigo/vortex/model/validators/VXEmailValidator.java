package io.vertigo.vortex.model.validators;

import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.VXValidator;

public record VXEmailValidator() implements VXValidator<String> {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return EMAIL_PATTERN.matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The email format is invalid");
	}
}
