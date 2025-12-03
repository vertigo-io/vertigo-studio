package io.vertigo.vortex.model.library.builders;

import java.time.Instant;

import io.vertigo.vortex.model.library.validators.time.VXInstantAfterValidator;
import io.vertigo.vortex.model.library.validators.time.VXInstantBeforeValidator;
import io.vertigo.vortex.model.library.validators.time.VXInstantFutureValidator;
import io.vertigo.vortex.model.library.validators.time.VXInstantPastValidator;
import io.vertigo.vortex.model.types.VXDataType;

public final class VXInstantBuilder extends VXAbstractDomainTypeBuilder<VXInstantBuilder> {
	VXInstantBuilder(final String name) {
		super(name, VXDataType.Instant);
	}

	public VXInstantBuilder after(final Instant instant) {
		return withValidator(new VXInstantAfterValidator(instant));
	}

	public VXInstantBuilder before(final Instant instant) {
		return withValidator(new VXInstantBeforeValidator(instant));
	}

	public VXInstantBuilder future() {
		return withValidator(new VXInstantFutureValidator());
	}

	public VXInstantBuilder past() {
		return withValidator(new VXInstantPastValidator());
	}
}
