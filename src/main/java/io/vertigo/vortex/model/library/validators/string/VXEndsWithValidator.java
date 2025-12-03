package io.vertigo.vortex.model.library.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.VXProps;
import io.vertigo.vortex.model.library.types.VXProperty;
import io.vertigo.vortex.model.library.types.VXValidator;

public record VXEndsWithValidator(String suffix) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.endsWith(suffix);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must end with '" + suffix + "'.");
	}

	@Override
	public VXProperty<String> getProperty() {
		return VXProps.ENDS_WITH.build(suffix);
	}
}
