package io.vertigo.vortex.model.types.validators.time;

import java.time.Instant;
import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXInstantAfterValidator(Instant instant) implements VXValidator<Instant, Instant> {

	@Override
	public boolean isValid(final Instant value) {
		if (value == null) {
			return true;
		}
		return value.isAfter(instant);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The instant must be after " + instant);
	}

	@Override
	public Optional<VxProperty<Instant>> getProperty() {
		return Optional.empty();
	}
}
