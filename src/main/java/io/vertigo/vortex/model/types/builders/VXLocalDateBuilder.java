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
		return withValidator(new VXLocalDateAfterValidator(date));
	}

	public VXLocalDateBuilder before(final LocalDate date) {
		return withValidator(new VXLocalDateBeforeValidator(date));
	}

	public VXLocalDateBuilder future() {
		return withValidator(new VXLocalDateFutureValidator());
	}

	public VXLocalDateBuilder past() {
		return withValidator(new VXLocalDatePastValidator());
	}
}
