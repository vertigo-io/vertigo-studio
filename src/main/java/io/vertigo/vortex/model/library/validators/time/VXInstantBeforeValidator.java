package io.vertigo.vortex.model.library.validators.time;

import java.time.Instant;
import java.time.temporal.Temporal;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.validators.VXProps;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXValidator;

public record VXInstantBeforeValidator(Instant instant) implements VXValidator<Instant, Temporal> {

	@Override
	public boolean isValid(final Instant value) {
		return value == null
				|| value.isBefore(instant);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The instant must be before " + instant);
	}

	@Override
	public VXProperty<Temporal> getProperty() {
		return VXProps.BEFORE.build(instant);
	}
}
