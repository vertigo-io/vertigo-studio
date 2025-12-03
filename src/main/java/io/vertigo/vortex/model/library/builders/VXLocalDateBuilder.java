package io.vertigo.vortex.model.library.builders;

import java.time.LocalDate;

import io.vertigo.vortex.model.library.types.VXDataType;
import io.vertigo.vortex.model.library.validators.date.VXLocalDateAfterValidator;
import io.vertigo.vortex.model.library.validators.date.VXLocalDateBeforeValidator;
import io.vertigo.vortex.model.library.validators.date.VXLocalDateFutureValidator;
import io.vertigo.vortex.model.library.validators.date.VXLocalDatePastValidator;

public final class VXLocalDateBuilder extends VXAbstractDomainTypeBuilder<VXLocalDateBuilder> {
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
