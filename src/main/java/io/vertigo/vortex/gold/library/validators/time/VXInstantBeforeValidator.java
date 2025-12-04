package io.vertigo.vortex.gold.library.validators.time;

import java.time.Instant;
import java.time.temporal.Temporal;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

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
