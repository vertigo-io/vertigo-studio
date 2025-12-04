package io.vertigo.vortex.gold.library.validators.string;

import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.gold.library.VXProps;
import io.vertigo.vortex.gold.library.types.VXProperty;
import io.vertigo.vortex.gold.library.types.VXValidator;

public record VXPatternValidator(String pattern) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| Pattern.compile(pattern).matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value does not match the expected pattern");
	}

	@Override
	public VXProperty<String> getProperty() {
		return VXProps.PATTERN.build(pattern);
	}
}
