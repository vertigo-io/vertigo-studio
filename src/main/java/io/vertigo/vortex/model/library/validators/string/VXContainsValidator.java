package io.vertigo.vortex.model.library.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.library.VXProps;
import io.vertigo.vortex.model.library.types.VXProperty;
import io.vertigo.vortex.model.library.types.VXValidator;

public record VXContainsValidator(String subString) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| value.contains(subString);
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must contain '" + subString + "'.");
	}

	@Override
	public VXProperty<String> getProperty() {
		return VXProps.CONTAINS.build(subString);
	}
}
