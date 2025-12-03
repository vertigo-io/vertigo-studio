package io.vertigo.vortex.model.types.validators.string;

import java.util.Optional;
import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.model.types.VXValidator;
import io.vertigo.vortex.model.types.VxProperty;
import io.vertigo.vortex.model.types.VxProps;

public record VXPatternValidator(String pattern) implements VXValidator<String, String> {

	@Override
	public boolean isValid(final String value) {
		if (value == null) {
			return true;
		}
		return Pattern.compile(pattern).matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value does not match the expected pattern");
	}

	@Override
	public Optional<VxProperty<String>> getProperty() {
		return Optional.of(new VxProperty<>(VxProps.PATTERN, String.class));
	}
}
