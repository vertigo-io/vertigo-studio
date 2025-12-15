package io.vertigo.vortex.notebook.library.validators.date;

import java.time.LocalDate;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.notebook.library.VXProps;
import io.vertigo.vortex.notebook.library.types.VXProperty;
import io.vertigo.vortex.notebook.library.types.VXValidator;

public record VXLocalDateFutureValidator() implements VXValidator<LocalDate, Boolean> {

	@Override
	public boolean isValid(final LocalDate value) {
		return value == null
				|| value.isAfter(LocalDate.now());
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The date must be in the future.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProps.FUTURE.build(true);
	}
}
