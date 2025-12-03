package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;
import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXInstantFutureValidator() implements VXValidator<Instant, Void> {

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
	public Optional<VxProperty<Void>> getProperty() {
		return Optional.empty();
	}
}
