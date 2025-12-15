package io.vertigo.vortex.notebook.library.validators.number;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.notebook.library.VXProps;
import io.vertigo.vortex.notebook.library.types.VXProperty;
import io.vertigo.vortex.notebook.library.types.VXValidator;

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
		return VXProps.MAX.build(max);
	}
}
