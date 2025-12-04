package io.vertigo.vortex.gold.library.validators.time;

import java.time.Instant;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

public record VXInstantPastValidator() implements VXValidator<Instant, Boolean> {

	@Override
	public boolean isValid(final Instant value) {
		return value == null
				|| value.isBefore(Instant.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The instant must be in the past.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProps.PAST.build(true);
	}
}
