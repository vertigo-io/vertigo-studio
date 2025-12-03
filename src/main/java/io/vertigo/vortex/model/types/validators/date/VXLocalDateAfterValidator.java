package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXLocalDateAfterValidator(LocalDate date) implements VXValidator<LocalDate, LocalDate> {

	@Override
	public boolean isValid(final LocalDate value) {
		if (value == null) {
			return true;
		}
		return value.isAfter(date);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be after " + date);
	}

	@Override
	public Optional<VxProperty<LocalDate>> getProperty() {
		return Optional.empty();
	}
}
