package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.validators.string.VXContainsValidator;
import io.vertigo.vortex.model.types.validators.string.VXEmailValidator;
import io.vertigo.vortex.model.types.validators.string.VXEndsWithValidator;
import io.vertigo.vortex.model.types.validators.string.VXExactLengthValidator;
import io.vertigo.vortex.model.types.validators.string.VXJsonValidator;
import io.vertigo.vortex.model.types.validators.string.VXMaxLengthValidator;
import io.vertigo.vortex.model.types.validators.string.VXMinLengthValidator;
import io.vertigo.vortex.model.types.validators.string.VXPatternValidator;
import io.vertigo.vortex.model.types.validators.string.VXStartsWithValidator;
import io.vertigo.vortex.model.types.validators.string.VXUrlValidator;

public final class VXStringBuilder extends AbstractVXDomainTypeBuilder<VXStringBuilder> {
	public VXStringBuilder(final String name) {
		super(name, VXDataType.String);
	}

	//--- Length

	public VXStringBuilder minLength(final int min) {
		_validators.add(new VXMinLengthValidator(min));
		return self();
	}

	public VXStringBuilder maxLength(final int max) {
		_validators.add(new VXMaxLengthValidator(max));
		return self();
	}

	public VXStringBuilder exactLength(final int length) {
		_validators.add(new VXExactLengthValidator(length));
		return self();
	}

	//--- Patterns

	public VXStringBuilder pattern(final String pattern) {
		_validators.add(new VXPatternValidator(pattern));
		return self();
	}

	public VXStringBuilder startsWith(final String prefix) {
		_validators.add(new VXStartsWithValidator(prefix));
		return self();
	}

	public VXStringBuilder endsWith(final String suffix) {
		_validators.add(new VXEndsWithValidator(suffix));
		return self();
	}

	public VXStringBuilder contains(final String subString) {
		_validators.add(new VXContainsValidator(subString));
		return self();
	}

	//--- Formats
	public VXStringBuilder email() {
		_validators.add(new VXEmailValidator());
		return self();
	}

	public VXStringBuilder url() {
		_validators.add(new VXUrlValidator());
		return self();
	}

	public VXStringBuilder json() {
		_validators.add(new VXJsonValidator());
		return self();
	}
}
