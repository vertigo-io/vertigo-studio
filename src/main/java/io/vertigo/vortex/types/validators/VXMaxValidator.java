package io.vertigo.vortex.types.validators;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.types.VXValidator;
import io.vertigo.vortex.types.VxProperty;
import io.vertigo.vortex.types.VxProps;

public record VXMaxValidator(Number max) implements VXValidator<Number, Number> {

	@Override
	public boolean isValid(final Number value) {
		if (value == null) {
			return true;
		}
		if (value instanceof Integer || value instanceof Long) {
			return value.longValue() <= max.longValue();
		}
		return value.doubleValue() <= max.doubleValue();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must be less than or equal to " + max);
	}

	@Override
	public Optional<VxProperty<Number>> getProperty() {
		return Optional.of(new VxProperty<>(VxProps.MAX, Number.class));
	}
}
