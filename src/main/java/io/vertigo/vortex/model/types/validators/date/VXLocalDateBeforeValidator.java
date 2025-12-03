package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXLocalDateBeforeValidator(LocalDate date) implements VXValidator<LocalDate, Temporal> {

	@Override
	public boolean isValid(final LocalDate value) {
		if (value == null) {
			return true;
		}
		return value.isBefore(date);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be before " + date);
	}

	@Override
	public VXProperty<Temporal> getProperty() {
		return VXProperty.of(VXProps.BEFORE, date);
	}
}
