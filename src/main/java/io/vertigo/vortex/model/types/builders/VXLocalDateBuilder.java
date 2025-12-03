package io.vertigo.vortex.model.types.builders;

import java.time.LocalDate;

import io.vertigo.vortex.model.types.VXDataType;
import io.vertigo.vortex.model.types.validators.date.VXLocalDateAfterValidator;
import io.vertigo.vortex.model.types.validators.date.VXLocalDateBeforeValidator;
import io.vertigo.vortex.model.types.validators.date.VXLocalDateFutureValidator;
import io.vertigo.vortex.model.types.validators.date.VXLocalDatePastValidator;

public final class VXLocalDateBuilder extends AbstractVXDomainTypeBuilder<VXLocalDateBuilder> {
	public VXLocalDateBuilder(final String name) {
		super(name, VXDataType.LocalDate);
	}

	public VXLocalDateBuilder after(final LocalDate date) {
		_validators.add(new VXLocalDateAfterValidator(date));
		return self();
	}

	public VXLocalDateBuilder before(final LocalDate date) {
		_validators.add(new VXLocalDateBeforeValidator(date));
		return self();
	}

	public VXLocalDateBuilder future() {
		_validators.add(new VXLocalDateFutureValidator());
		return self();
	}

	public VXLocalDateBuilder past() {
		_validators.add(new VXLocalDatePastValidator());
		return self();
	}
}
