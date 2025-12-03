package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;
import io.vertigo.vortex.model.types.VxProps;

public record VXMinLengthValidator(int min) implements VXValidator<String, Integer> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.length() >= min;
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The length must be greater than or equal to " + min);
	}

	@Override
	public Optional<VxProperty<Integer>> getProperty() {
		return Optional.of(new VxProperty<>(VxProps.MIN_LENGTH, Integer.class));
	}
}
