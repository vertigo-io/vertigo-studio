package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;

public record VXStartsWithValidator(String prefix) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return value.startsWith(prefix);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must start with '" + prefix + "'.");
	}

	@Override
	public Optional<VxProperty<String>> getProperty() {
		return Optional.empty();
	}
}
