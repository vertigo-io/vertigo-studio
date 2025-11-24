package io.vertigo.vortex.model.validators;

import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.VXValidator;

public record VXPatternValidator(String pattern) implements VXValidator<String> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return Pattern.compile(pattern).matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value does not match the expected pattern");
	}
}
