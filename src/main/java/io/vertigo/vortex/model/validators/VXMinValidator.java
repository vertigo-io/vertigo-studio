package io.vertigo.vortex.model.validators;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.VXValidator;

public record VXMinValidator(Number min) implements VXValidator<Number> {

	@Override
	public boolean isValid(final Number value) {
		if (value == null) {
			return true;
		}
		if (value instanceof Integer || value instanceof Long) {
			return value.longValue() >= min.longValue();
		}
		return value.doubleValue() >= min.doubleValue();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must be greater than or equal to " + min);
	}
}
