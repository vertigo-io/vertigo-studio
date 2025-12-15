package io.vertigo.vortex.notebook.library.validators.string;

import java.util.regex.Pattern;

import io.vertigo.core.locale.LocaleMessageText;
import io.vertigo.vortex.notebook.library.VXProps;
import io.vertigo.vortex.notebook.library.types.VXProperty;
import io.vertigo.vortex.notebook.library.types.VXValidator;

public record VXEmailValidator() implements VXValidator<String, Boolean> {
	private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	@Override
	public boolean isValid(final String value) {
		return value == null
				|| EMAIL_PATTERN.matcher(value).matches();
	}

	@Override
	public LocaleMessageText getErrorMessage() {
		return LocaleMessageText.of("The value must be a valid email address.");
	}

	@Override
	public VXProperty<Boolean> getProperty() {
		return VXProps.EMAIL.build(true);
	}
}
