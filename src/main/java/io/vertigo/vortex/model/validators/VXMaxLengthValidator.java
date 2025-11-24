package io.vertigo.vortex.model.validators;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.VXValidator;

public record VXMaxLengthValidator(int max) implements VXValidator<String> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.length() <= max;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The length must be less than or equal to " + max);
	}
}
