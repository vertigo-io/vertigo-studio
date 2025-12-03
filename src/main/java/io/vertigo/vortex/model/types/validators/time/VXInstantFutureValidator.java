package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXInstantFutureValidator() implements VXValidator<Instant, Boolean> {

	@Override
	public boolean isValid(final Instant value) {
		if (value == null) {
			return true;
		}
		return value.isAfter(Instant.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The instant must be in the future.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProperty.of(VXProps.FUTURE, true);
	}
}
