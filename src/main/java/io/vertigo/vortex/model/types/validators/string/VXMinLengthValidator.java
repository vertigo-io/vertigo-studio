package io.vertigo.vortex.model.types.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXMinLengthValidator(int min) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.length() >= min;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The length must be greater than or equal to " + min);
	}

	@Override
	public VXProperty<Integer> getProperty() {
		return VXProps.MIN_LENGTH.build(min);
	}
}
