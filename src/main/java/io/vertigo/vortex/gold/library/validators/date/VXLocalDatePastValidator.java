package io.vertigo.vortex.gold.library.validators.date;

import java.time.LocalDate;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

public record VXLocalDatePastValidator() implements VXValidator<LocalDate, Boolean> {

	@Override
	public boolean isValid(final LocalDate value) {
		return value == null
				|| value.isBefore(LocalDate.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be in the past.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProps.PAST.build(true);
	}
}
