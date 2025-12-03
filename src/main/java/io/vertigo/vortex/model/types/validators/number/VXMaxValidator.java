package io.vertigo.vortex.model.types.validators.number;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

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
	public VXProperty<Number> getProperty() {
		return VXProperty.of(VXProps.MAX, max);
	}
}
