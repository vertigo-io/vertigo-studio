package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.validators.string.VXContainsValidator;
import io.vertigo.vortex.gold.library.validators.string.VXEmailValidator;
import io.vertigo.vortex.gold.library.validators.string.VXEndsWithValidator;
import io.vertigo.vortex.gold.library.validators.string.VXExactLengthValidator;
import io.vertigo.vortex.gold.library.validators.string.VXJsonValidator;
import io.vertigo.vortex.gold.library.validators.string.VXMaxLengthValidator;
import io.vertigo.vortex.gold.library.validators.string.VXMinLengthValidator;
import io.vertigo.vortex.gold.library.validators.string.VXPatternValidator;
import io.vertigo.vortex.gold.library.validators.string.VXStartsWithValidator;
import io.vertigo.vortex.gold.library.validators.string.VXUrlValidator;

public final class VXStringBuilder extends VXAbstractDomainTypeBuilder<VXStringBuilder> {
	public VXStringBuilder(final VXKey libraryKey, final String name) {
		super(libraryKey, name, VXDataType.String);
	}

	//--- Length

	public VXStringBuilder minLength(final int min) {
		return withValidator(new VXMinLengthValidator(min));
	}

	public VXStringBuilder maxLength(final int max) {
		return withValidator(new VXMaxLengthValidator(max));
	}

	public VXStringBuilder exactLength(final int length) {
		return withValidator(new VXExactLengthValidator(length));
	}

	//--- Patterns

	public VXStringBuilder pattern(final String pattern) {
		return withValidator(new VXPatternValidator(pattern));
	}

	public VXStringBuilder startsWith(final String prefix) {
		return withValidator(new VXStartsWithValidator(prefix));
	}

	public VXStringBuilder endsWith(final String suffix) {
		return withValidator(new VXEndsWithValidator(suffix));
	}

	public VXStringBuilder contains(final String subString) {
		return withValidator(new VXContainsValidator(subString));
	}

	//--- Formats
	public VXStringBuilder email() {
		return withValidator(new VXEmailValidator());
	}

	public VXStringBuilder url() {
		return withValidator(new VXUrlValidator());
	}

	public VXStringBuilder json() {
		return withValidator(new VXJsonValidator());
	}
}
