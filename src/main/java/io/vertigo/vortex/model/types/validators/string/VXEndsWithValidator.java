package io.vertigo.vortex.model.types.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXProperty;
import io.vertigo.vortex.model.types.VXProps;
import io.vertigo.vortex.model.types.VXValidator;

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
	public VXProperty<String> getProperty() {
		return VXProperty.of(VXProps.ENDS_WITH, suffix);
	}
}
