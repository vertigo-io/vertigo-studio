package io.vertigo.vortex.model.library.builders;

import io.vertigo.vortex.model.library.validators.number.VXMaxValidator;
import io.vertigo.vortex.model.library.validators.number.VXMinValidator;
import io.vertigo.vortex.model.types.VXDataType;

public final class VXNumberBuilder extends VXAbstractDomainTypeBuilder<VXNumberBuilder> {
	VXNumberBuilder(final String name, final VXDataType dataType) {
		super(name, dataType);
	}

	public VXNumberBuilder min(final Number min) {
		return withValidator(new VXMinValidator(min));
	}

	public VXNumberBuilder max(final Number max) {
		return withValidator(new VXMaxValidator(max));
	}
}
