package io.vertigo.vortex.model.library.validators.date;

import java.time.LocalDate;
import java.time.temporal.Temporal;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.VXProps;
import io.vertigo.vortex.model.library.types.VXProperty;
import io.vertigo.vortex.model.library.types.VXValidator;

public record VXLocalDateAfterValidator(LocalDate date) implements VXValidator<LocalDate, Temporal> {

	@Override
	public boolean isValid(final LocalDate value) {
		return value == null
				|| value.isAfter(date);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be after " + date);
	}

	@Override
	public VXProperty<Temporal> getProperty() {
		return VXProps.AFTER.build(date);
	}
}
