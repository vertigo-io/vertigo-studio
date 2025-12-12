package io.vertigo.vortex.gold.library.builders;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.validators.number.VXMaxValidator;
import io.vertigo.vortex.gold.library.validators.number.VXMinValidator;

public final class VXNumberBuilder extends VXAbstractDomainTypeBuilder<VXNumberBuilder> {
	public VXNumberBuilder(final VXKey libraryKey, final String name, final VXDataType dataType) {
		super(libraryKey, name, dataType);
	}

	public VXNumberBuilder min(final Number min) {
		return withValidator(new VXMinValidator(min));
	}

	public VXNumberBuilder max(final Number max) {
		return withValidator(new VXMaxValidator(max));
	}
}
