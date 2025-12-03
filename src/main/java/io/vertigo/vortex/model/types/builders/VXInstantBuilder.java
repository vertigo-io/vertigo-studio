package io.vertigo.vortex.model.types.builders;

import java.time.Instant;

import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.validators.time.VXInstantAfterValidator;
import io.vertigo.vortex.model.types.validators.time.VXInstantBeforeValidator;
import io.vertigo.vortex.model.types.validators.time.VXInstantFutureValidator;
import io.vertigo.vortex.model.types.validators.time.VXInstantPastValidator;

public final class VXInstantBuilder extends AbstractVXDomainTypeBuilder<VXInstantBuilder> {
	public VXInstantBuilder(final String name) {
		super(name, VXDataType.Instant);
	}

	public VXInstantBuilder after(final Instant instant) {
		_validators.add(new VXInstantAfterValidator(instant));
		return self();
	}

	public VXInstantBuilder before(final Instant instant) {
		_validators.add(new VXInstantBeforeValidator(instant));
		return self();
	}

	public VXInstantBuilder future() {
		_validators.add(new VXInstantFutureValidator());
		return self();
	}

	public VXInstantBuilder past() {
		_validators.add(new VXInstantPastValidator());
		return self();
	}
}
