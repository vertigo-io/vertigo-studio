package io.vertigo.vortex.model.library.validators.date;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.validators.VXProps;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXValidator;

public record VXLocalDateBeforeValidator(LocalDate date) implements VXValidator<LocalDate, Temporal> {

	@Override
	public boolean isValid(final LocalDate value) {
		return value == null
				|| value.isBefore(date);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be before " + date);
	}

	@Override
	public VXProperty<Temporal> getProperty() {
		return VXProps.BEFORE.build(date);
	}
}
