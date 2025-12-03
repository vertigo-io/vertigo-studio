package io.vertigo.vortex.model.types.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

public record VXStartsWithValidator(String prefix) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.startsWith(prefix);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must start with '" + prefix + "'.");
	}

	@Override
	public VXProperty<String> getProperty() {
		return VXProps.STARTS_WITH.build(prefix);
	}
}
