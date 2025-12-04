package io.vertigo.vortex.gold.library.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

public record VXMaxLengthValidator(int maxLength) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.length() <= maxLength;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must be less than or equal to " + maxLength + " characters.");
	}

	@Override
	public VXProperty<Integer> getProperty() {
		return VXProps.MAX_LENGTH.build(maxLength);
	}
}
