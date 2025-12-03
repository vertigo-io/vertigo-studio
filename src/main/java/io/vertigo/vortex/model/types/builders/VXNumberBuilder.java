package io.vertigo.vortex.model.types.builders;

import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.validators.number.VXMaxValidator;
import io.vertigo.vortex.model.types.validators.number.VXMinValidator;

public final class VXNumberBuilder extends AbstractVXDomainTypeBuilder<VXNumberBuilder> {
	public VXNumberBuilder(final String name, final VXDataType dataType) {
		super(name, dataType);
	}

	public VXNumberBuilder min(final Number min) {
		return withValidator(new VXMinValidator(min));
	}

	public VXNumberBuilder max(final Number max) {
		return withValidator(new VXMaxValidator(max));
	}
}
