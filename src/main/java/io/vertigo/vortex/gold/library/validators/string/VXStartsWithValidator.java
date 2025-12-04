package io.vertigo.vortex.gold.library.validators.string;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

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
