package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;
import io.vertigo.vortex.model.types.VxProps;

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
	public Optional<VxProperty<Integer>> getProperty() {
		return Optional.of(new VxProperty<>(VxProps.MAX_LENGTH, Integer.class));
	}
}
