package io.vertigo.vortex.model.library.builders;

import java.math.BigDecimal;

import io.vertigo.vortex.model.library.validators.number.VXMaxValidator;
import io.vertigo.vortex.model.library.validators.number.VXMinValidator;
import io.vertigo.vortex.model.types.VXDataType;

public final class VXBigDecimalBuilder extends VXAbstractDomainTypeBuilder<VXBigDecimalBuilder> {
	VXBigDecimalBuilder(final String name) {
		super(name, VXDataType.BigDecimal);
	}

	public VXBigDecimalBuilder min(final BigDecimal min) {
		return withValidator(new VXMinValidator(min));
	}

	public VXBigDecimalBuilder max(final BigDecimal max) {
		return withValidator(new VXMaxValidator(max));
	}
}
