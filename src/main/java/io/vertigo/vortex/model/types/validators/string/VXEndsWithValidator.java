package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXEndsWithValidator(String suffix) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.endsWith(suffix);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must end with '" + suffix + "'.");
	}

	@Override
	public Optional<VxProperty<String>> getProperty() {
		return Optional.empty();
	}
}
