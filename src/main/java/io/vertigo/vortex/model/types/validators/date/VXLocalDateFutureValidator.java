package io.vertigo.vortex.model.types.validators.date;

import java.time.LocalDate;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXLocalDateFutureValidator() implements VXValidator<LocalDate, Boolean> {

	@Override
	public boolean isValid(final LocalDate value) {
		if (value == null) {
			return true;
		}
		return value.isAfter(LocalDate.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be in the future.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProperty.of(VXProps.FUTURE, true);
	}
}
