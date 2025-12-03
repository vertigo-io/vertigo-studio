package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXLocalDatePastValidator() implements VXValidator<LocalDate, Void> {

	@Override
	public boolean isValid(final LocalDate value) {
		if (value == null) {
			return true;
		}
		return value.isBefore(LocalDate.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be in the past.");
	}

	@Override
	public Optional<VxProperty<Void>> getProperty() {
		return Optional.empty();
	}
}
