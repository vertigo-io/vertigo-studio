package io.vertigo.vortex.model.types.validators.number;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXMinValidator(Number min) implements VXValidator<Number, Number> {

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

	@Override
	public VXProperty<Number> getProperty() {
		return VXProps.MIN.build(min);
	}
}
