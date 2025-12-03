package io.vertigo.vortex.model.library.validators.time;

import java.time.Instant;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.validators.VXProps;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXValidator;

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
