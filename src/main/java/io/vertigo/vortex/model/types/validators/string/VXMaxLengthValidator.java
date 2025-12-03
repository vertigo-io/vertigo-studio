package io.vertigo.vortex.model.types.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXMaxLengthValidator(int maxLength) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.length() <= maxLength;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must be less than or equal to " + maxLength + " characters.");
	}

	@Override
	public VXProperty<Integer> getProperty() {
		return VXProperty.of(VXProps.MAX_LENGTH, maxLength);
	}
}
