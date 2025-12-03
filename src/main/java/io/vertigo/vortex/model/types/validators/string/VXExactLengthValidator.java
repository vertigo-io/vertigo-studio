package io.vertigo.vortex.model.types.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXExactLengthValidator(int length) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.length() == length;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The length must be exactly " + length + " characters.");
	}

	@Override
	public VXProperty<Integer> getProperty() {
		return VXProps.EXACT_LENGTH.build(length);
	}
}
