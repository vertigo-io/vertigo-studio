package io.vertigo.vortex.gold.library.builders;

import java.time.Instant;

import io.vertigo.vortex.gold.VXKey;
import io.vertigo.vortex.gold.library.types.VXDataType;
import io.vertigo.vortex.gold.library.validators.time.VXInstantAfterValidator;
import io.vertigo.vortex.gold.library.validators.time.VXInstantBeforeValidator;
import io.vertigo.vortex.gold.library.validators.time.VXInstantFutureValidator;
import io.vertigo.vortex.gold.library.validators.time.VXInstantPastValidator;

public final class VXInstantBuilder extends VXAbstractDomainTypeBuilder<VXInstantBuilder> {
	public VXInstantBuilder(final VXKey libraryKey, final String name) {
		super(libraryKey, name, VXDataType.Instant);
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
